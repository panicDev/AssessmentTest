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
import id.panicdev.core.data.local.AppDatabase
import id.panicdev.core.data.remote.api.GitHubApiService
import javax.inject.Inject

class RepositoryRemoteMediatorFactory @Inject constructor(
    private val database: AppDatabase,
    private val apiService: GitHubApiService,
) {
    fun create(query: String, context: Context): RepositoryRemoteMediator {
        return RepositoryRemoteMediator(context, database, apiService, query)
    }
}
