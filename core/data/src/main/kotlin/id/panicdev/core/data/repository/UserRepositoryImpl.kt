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

import id.panicdev.core.data.domain.model.Repository
import id.panicdev.core.data.domain.model.User
import id.panicdev.core.data.domain.repository.UserRepository
import id.panicdev.core.data.local.UserDao
import id.panicdev.core.data.mapper.toDomain
import id.panicdev.core.data.mapper.toEntity
import id.panicdev.core.data.remote.network.GithubService
import id.panicdev.core.data.remote.network.resultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GithubService,
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun getUsers(query: String?): Flow<List<User>> {
        val roomFlow = userDao.getUsers(query = query).map { list -> list.map { it.toDomain() } }
        val apiFlow = getUsersFromApi(query = query)

        return roomFlow.combine(apiFlow) { list, _ ->
            list
        }
    }

    override suspend fun getUser(username: String): User? {
        return api.getUser(username = username)?.toDomain()
    }

    override suspend fun getFollowers(username: String): List<User> {
        return api.getFollowers(username = username).map {
            it.toDomain()
        }
    }

    override suspend fun getRepositories(username: String): List<Repository> {
        return api.getRepos(username = username).map {
            it.toDomain()
        }
    }

    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users = users.map { it.toEntity() })
    }

    private fun getUsersFromApi(query: String?): Flow<List<User>> {
        return flow {
            resultOf {
                val result = withContext(Dispatchers.IO) {
                    val users = if (query.isNullOrEmpty()) {
                        api.getUsers().map { it.toDomain() }
                    } else {
                        api.getUsersBy(query).map { it.toDomain() }
                    }
                    userDao.insertUsers(users.map { it.toEntity() })
                    users
                }
                result
            }
            emit(emptyList<User>())
        }.onStart {
            emit(emptyList())
        }
    }
}
