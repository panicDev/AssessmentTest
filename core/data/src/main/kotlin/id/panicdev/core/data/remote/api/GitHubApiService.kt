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

import id.panicdev.core.data.remote.model.ReposResponse
import id.panicdev.core.data.remote.model.UserQueryResponse
import id.panicdev.core.data.remote.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @GET("search/users")
    suspend fun getUsersBy(@Query("q") query: String): UserQueryResponse

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserResponse?

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<UserResponse>

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<ReposResponse>
}
