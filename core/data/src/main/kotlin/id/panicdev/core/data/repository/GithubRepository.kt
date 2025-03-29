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

import androidx.paging.PagingData
import id.panicdev.core.data.local.entity.RepositoryEntity
import id.panicdev.core.data.remote.dto.ContributorDto
import id.panicdev.core.data.remote.dto.RepositoryDetailsDto
import id.panicdev.core.data.remote.dto.RepositoryDto
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getGitRepoSearchPagingSource(query: String): Flow<PagingData<RepositoryEntity>>

    suspend fun getGitRepoDetails(owner: String, repo: String): RepositoryDetailsDto

    suspend fun getContributors(owner: String, repo: String): List<ContributorDto>

    suspend fun getUserRepositories(username: String): List<RepositoryDto>
}
