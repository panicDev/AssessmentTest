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
package id.panicdev.core.data.mapper

import id.panicdev.core.data.domain.model.User
import id.panicdev.core.data.local.entity.UserEntity
import id.panicdev.core.data.remote.model.UserResponse

fun UserResponse.toDomain(): User {
    return User(
        id = this.id,
        username = this.username.orEmpty(),
        avatarUrl = this.avatarUrl.orEmpty(),
        name = this.name,
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        username = this.username.orEmpty(),
        avatarUrl = this.avatarUrl.orEmpty(),
        name = this.name,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id ?: 0,
        username = this.username.orEmpty(),
        avatarUrl = this.avatarUrl.orEmpty(),
        name = this.name.orEmpty(),
    )
}
