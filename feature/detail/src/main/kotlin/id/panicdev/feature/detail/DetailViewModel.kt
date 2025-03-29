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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.panicdev.core.data.domain.usecase.UserUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userUseCases: UserUseCases,
) : ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    init {
        savedStateHandle.get<String>("username")?.let { username ->
            getUser(username = username)
        }
    }

    private fun getUser(username: String) {
        state = state.copy(isLoading = true, followersError = null)
        viewModelScope.launch {
            val user = userUseCases.getUserUseCase(username = username)
            state = state.copy(user = user)
            if (user != null) {
                userUseCases.getFollowersUseCase(username = username)
                    .onSuccess { followers ->
                        user.followers = followers
                        state = state.copy(user = user)
                    }.onFailure {
                        state = state.copy(followersError = it.message)
                    }
                userUseCases.getRepositoriesUseCase(username = username)
                    .onSuccess { repos ->
                        user.repos = repos
                        state = state.copy(user = user)
                    }.onFailure {
                        state = state.copy(reposError = it.message)
                    }
            }
            state = state.copy(isLoading = false)
        }
    }
}
