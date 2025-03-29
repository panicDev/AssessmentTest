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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.panicdev.core.data.local.entity.RepositoryEntity
import id.panicdev.core.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository,
) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "QUERY_KEY"
        private const val DEFAULT_QUERY = ""
    }

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val searchQuery = savedStateHandle.getStateFlow(QUERY_KEY, DEFAULT_QUERY)

    fun onQueryChanged(query: String) {
        savedStateHandle[QUERY_KEY] = query
    }

    fun onSearchQueryClick() {
        try {
            _uiState.update { HomeUiState.Loading }

            val data = repository.getGitRepoSearchPagingSource(searchQuery.value)
                .cachedIn(viewModelScope)

            _uiState.update { HomeUiState.Success(data) }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching git repository search results")
            _uiState.value = HomeUiState.Error("Something went wrong!")
        }
    }
}

sealed class HomeUiState {
    data object Idle : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val data: Flow<PagingData<RepositoryEntity>>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
