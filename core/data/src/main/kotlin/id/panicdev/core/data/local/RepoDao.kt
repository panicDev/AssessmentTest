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
package id.panicdev.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import id.panicdev.core.data.local.entity.RepositoryEntity

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo WHERE `query` = :query")
    fun getGitRepoSearchPagingSource(query: String): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repo WHERE `query` = :query")
    suspend fun clearQueryData(query: String)

    @Upsert
    suspend fun upsertRepository(repositories: List<RepositoryEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM repo WHERE `query` = :query)")
    suspend fun isQueryInDb(query: String): Boolean
}
