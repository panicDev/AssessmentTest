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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import id.panicdev.core.data.domain.model.User
import timber.log.Timber

@Composable
fun UserFollowers(
    followers: List<User>
) {
    Timber.d("UserFollowers ${followers.size}")
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(followers) { follower ->
            Column(modifier = Modifier.widthIn(max = 64.dp)) {
                GlideImage(
                    imageModel = { follower.avatarUrl },
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    component = rememberImageComponent {
                        +ShimmerPlugin(
                            Shimmer.Flash(
                                baseColor = Color.White,
                                highlightColor = Color.LightGray
                            )
                        )
                    },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                    )
                )

                Text(
                    text = follower.username.orEmpty(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}