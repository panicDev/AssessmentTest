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
package id.panicdev.core.data.remote.api

import id.panicdev.core.data.remote.dto.ContributorDto
import id.panicdev.core.data.remote.dto.RepoSearchResponseDto
import id.panicdev.core.data.remote.dto.RepositoryDetailsDto
import id.panicdev.core.data.remote.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): RepoSearchResponseDto

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Long = 10,
    ): RepositoryDetailsDto

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Long = 10,
    ): List<ContributorDto>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Long = 10,
    ): List<RepositoryDto>
}
