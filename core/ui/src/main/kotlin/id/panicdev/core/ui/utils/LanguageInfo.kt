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
package id.panicdev.core.ui.utils

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LanguageInfo(
    val color: String,
    val url: String,
)

fun loadColorsFromJson(context: Context): Map<String, LanguageInfo> {
    return try {
        val jsonString = context.assets.open("colors.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<Map<String, LanguageInfo>>(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyMap()
    }
}
