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
package id.panicdev.feature.detail.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.panicdev.core.data.domain.model.User

@Composable
fun DetailContent(
    paddingValues: PaddingValues,
    user: User?
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            UserInfo(user = user)
        }
        item {
            Text(text = "Followers", style = MaterialTheme.typography.titleMedium)
        }
        item {

            UserFollowers(followers = user?.followers.orEmpty())
        }
        item {
            Text(text = "Repositories", style = MaterialTheme.typography.titleMedium)
        }
        userRepositories(repos = user?.repos.orEmpty())
    }
}