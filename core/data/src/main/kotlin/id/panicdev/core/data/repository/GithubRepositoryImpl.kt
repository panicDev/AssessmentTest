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
package id.panicdev.core.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.panicdev.core.data.local.RepoDao
import id.panicdev.core.data.local.entity.RepositoryEntity
import id.panicdev.core.data.remote.RepositoryRemoteMediatorFactory
import id.panicdev.core.data.remote.api.GitHubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val mediatorFactory: RepositoryRemoteMediatorFactory,
    private val context: Context,
    private val dao: RepoDao,
    private val apiService: GitHubApiService,
) : GithubRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGitRepoSearchPagingSource(query: String): Flow<PagingData<RepositoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10,
                prefetchDistance = 1,
            ),
            remoteMediator = mediatorFactory.create(query, context),
            pagingSourceFactory = {
                dao.getGitRepoSearchPagingSource(query)
            },
        ).flow
    }

    override suspend fun getGitRepoDetails(owner: String, repo: String) = withContext(Dispatchers.IO) {
        apiService.getRepositoryDetails(owner, repo, 1)
    }

    override suspend fun getContributors(owner: String, repo: String) = withContext(Dispatchers.IO) {
        apiService.getContributors(owner, repo, 1)
    }

    override suspend fun getUserRepositories(username: String) = withContext(Dispatchers.IO) {
        apiService.getUserRepositories(username, 1)
    }
}
