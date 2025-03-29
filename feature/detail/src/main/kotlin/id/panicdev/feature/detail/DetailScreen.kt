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


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import id.panicdev.feature.detail.components.DetailContent

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.user?.username.orEmpty())
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            DetailContent(
                paddingValues = paddingValues,
                user = state.user
            )
        }
    )
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(onBack = {})
}