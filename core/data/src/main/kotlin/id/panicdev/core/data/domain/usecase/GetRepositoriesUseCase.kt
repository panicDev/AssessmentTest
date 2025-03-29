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
package id.panicdev.core.data.domain.usecase

import id.panicdev.core.data.domain.model.Repository
import id.panicdev.core.data.domain.repository.UserRepository

class GetRepositoriesUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String?): Result<List<Repository>> {
        return if (username.isNullOrEmpty()) {
            Result.failure(Exception("Username is null or empty"))
        } else {
            val repos = userRepository.getRepositories(username = username)
            Result.success(repos)
        }
    }
}
