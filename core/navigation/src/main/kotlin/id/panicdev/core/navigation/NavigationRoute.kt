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
package com.jmenmar.githubusers.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationRoute(
    val route: String,
    val arguments: List<NamedNavArgument> = listOf(),
) {
    data object Home : NavigationRoute(route = "HOME")
    data object Detail : NavigationRoute(
        route = "DETAIL?username={username}",
        arguments = listOf(
            navArgument("username") {
                type = NavType.StringType
                nullable = false
            },
        ),
    ) {
        fun where(username: String) = "DETAIL?username=$username"
    }
}
