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
package id.panicdev.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.panicdev.core.data.domain.model.User
import id.panicdev.core.data.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>?>(null)
    var users = _users.asStateFlow()

    var state by mutableStateOf(HomeState())
        private set

    private var currentJob: Job? = null

    init {
        getUsers()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeQuery -> {
                state = state.copy(query = event.query)
                getUsers()
            }
            HomeEvent.ClearQuery -> {
                state = state.copy(query = "")
                getUsers()
            }
        }
    }

    private fun getUsers() {
        state = state.copy(isLoading = true)
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            getUsersUseCase(query = state.query).collectLatest {
                _users.value = it
                state = state.copy(isLoading = false)
            }
        }
    }
}
