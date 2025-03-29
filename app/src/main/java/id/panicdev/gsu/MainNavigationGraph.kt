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
import androidx.navigation.toRoute
import id.panicdev.core.navigation.ScreenRoute

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home,
    ) {
        composable<ScreenRoute.Home> {
            HomeScreen(navController = navController)
        }
        composable<ScreenRoute.Detail> {
            val arg = it.toRoute<ScreenRoute.Detail>()
            DetailScreen(navController = navController)
        }
        composable<ScreenRoute.WebView> {
            val arg = it.toRoute<ScreenRoute.WebView>()
            WebViewScreen(url = arg.url, onBackPressed = { navController.popBackStack() })
        }
    }
}
