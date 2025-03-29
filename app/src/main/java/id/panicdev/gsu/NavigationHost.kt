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
package id.panicdev.gsu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jmenmar.githubusers.navigation.NavigationRoute
import id.panicdev.feature.detail.DetailScreen
import id.panicdev.feature.home.HomeScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = "ROOT",
        startDestination = NavigationRoute.Home.route,
    ) {
        composable(route = NavigationRoute.Home.route) {
            HomeScreen(
                onDetail = { username ->
                    if (username.isNotEmpty()) {
                        navController.navigate(NavigationRoute.Detail.where(username = username))
                    }
                },
            )
        }

        composable(route = NavigationRoute.Detail.route) {
            DetailScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
