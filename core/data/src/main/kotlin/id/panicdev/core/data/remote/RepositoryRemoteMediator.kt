/*
 * Copyright 2025 Musthofa Ali
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package id.panicdev.core.data.remote

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.panicdev.core.data.local.AppDatabase
import id.panicdev.core.data.local.entity.RepositoryEntity
import id.panicdev.core.data.network.NetworkUtils
import id.panicdev.core.data.remote.api.GitHubApiService
import id.panicdev.core.data.remote.dto.toRepositoryEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val context: Context,
    private val database: AppDatabase,
    private val apiService: GitHubApiService,
    private val query: String,
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>,
    ): MediatorResult {
        return try {
            val isOnline = NetworkUtils.isNetworkAvailable(context)

            if (!isOnline && loadType == LoadType.REFRESH && database.repoDao().isQueryInDb(query)) {
                // Skip fetching and use cached data
                return MediatorResult.Success(endOfPaginationReached = false)
            }

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> {
                    val lastItem = state.pages.lastOrNull()?.data?.lastOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        // Calculate the next page number
                        state.pages.size + 1
                    }
                }

                else -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val gitRepository = apiService.searchRepositories(
                query = query,
                page = loadKey,
                perPage = state.config.pageSize,
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.repoDao().clearQueryData(query)
                }
                val gitRepositoryEntity = gitRepository.repositories.map {
                    it.toRepositoryEntity(query)
                }
                database.repoDao().upsertRepository(gitRepositoryEntity)
            }
            val isEndOfPagination = gitRepository.repositories.isEmpty()
            MediatorResult.Success(endOfPaginationReached = isEndOfPagination)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
