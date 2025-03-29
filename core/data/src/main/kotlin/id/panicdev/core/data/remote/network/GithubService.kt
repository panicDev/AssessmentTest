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
package id.panicdev.core.data.remote.network

import id.panicdev.core.data.remote.api.GitHubApiService
import id.panicdev.core.data.remote.model.ReposResponse
import id.panicdev.core.data.remote.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubService @Inject constructor(
    private val api: GitHubApiService,
) {
    suspend fun getUsers(): List<UserResponse> {
        return withContext(Dispatchers.IO) {
            api.getUsers()
        }
    }

    suspend fun getUsersBy(query: String): List<UserResponse> {
        return withContext(Dispatchers.IO) {
            api.getUsersBy(query = query).items
        }
    }

    suspend fun getUser(username: String): UserResponse? {
        return withContext(Dispatchers.IO) {
            api.getUser(username = username)
        }
    }

    suspend fun getFollowers(username: String): List<UserResponse> {
        return withContext(Dispatchers.IO) {
            api.getFollowers(username = username)
        }
    }

    suspend fun getRepos(username: String): List<ReposResponse> {
        return withContext(Dispatchers.IO) {
            api.getRepos(username = username)
        }
    }
}
