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
package id.panicdev.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.panicdev.core.data.domain.model.User

@Composable
fun UsersResults(
    isLoading: Boolean,
    users : List<User>?,
    onDetail: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (!isLoading) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(200.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = {
                    items(
                        count = users?.size ?: 0,
                    ) {index ->
                        val user = users?.get(index)
                        user?.let {
                            UserCard(
                                user = user,
                                onClick = { username ->
                                    onDetail(username)
                                }
                            )
                        }
                    }

                }
            )
        } else {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserResultsPreview() {
    UsersResults(
        isLoading = true,
        users = listOf(User(username = "abc")),
        onDetail = {}
    )
}