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
package id.panicdev.feature.detail

import id.panicdev.core.data.domain.model.Repository
import id.panicdev.core.data.domain.model.User

data class DetailState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val followers: List<User>? = emptyList(),
    val repos: List<Repository>? = emptyList(),
    val followersError: String? = null,
    val reposError: String? = null,
)
