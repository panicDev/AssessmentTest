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
package id.panicdev.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.panicdev.core.data.local.converter.OwnerTypeConvertor
import id.panicdev.core.data.local.entity.RepositoryEntity

@Database(
    entities = [
        RepositoryEntity::class,
    ],
    version = 1,
)
@TypeConverters(OwnerTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
