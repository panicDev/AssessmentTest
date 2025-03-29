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
package id.panicdev.core.data.remote.dto

import id.panicdev.core.data.local.entity.OwnerEntity
import id.panicdev.core.data.local.entity.RepositoryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("description") val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("owner") val owner: OwnerDto,
)

fun OwnerDto.toOwnerEntity(): OwnerEntity {
    return OwnerEntity(
        username = username,
        avatarUrl = avatarUrl,
    )
}

fun RepositoryDto.toRepositoryEntity(query: String): RepositoryEntity {
    return RepositoryEntity(
        id = id,
        name = name,
        query = query.lowercase(),
        fullName = fullName,
        description = description,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        owner = owner.toOwnerEntity(),
    )
}
