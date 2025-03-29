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
package id.panicdev.core.navigation.helper

import android.net.Uri
import android.os.Bundle
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

inline fun <reified T : Any> NavGraphBuilder.composableScreen(
    useDefaultTransition: Boolean = true,
    customArgs: Map<KType, NavType<*>>? = null,
    deepLinks: List<NavDeepLink>? = null,
    noinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) {
    composable<T>(
        typeMap = customArgs ?: emptyMap(),
        deepLinks = deepLinks ?: emptyList(),
        enterTransition = {
            if (useDefaultTransition) {
                slideIntoContainer(towards = Start, animationSpec = tween(350))
            } else {
                fadeIn(tween(350))
            }
        },
        exitTransition = {
            fadeOut(animationSpec = tween(350))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(350))
        },
        popExitTransition = {
            if (useDefaultTransition) {
                slideOutOfContainer(towards = End, animationSpec = tween(350))
            } else {
                fadeOut(tween(350))
            }
        },
        content = content,
    )
}

inline fun <reified T : Any> customNavType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) = bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = Uri.encode(json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}

inline fun <reified T : Any> generateCustomNavType() = typeOf<T>() to customNavType<T>()

inline fun <reified T> NavController.setBackPressedWithArgs(
    key: String,
    value: T,
) = previousBackStackEntry?.savedStateHandle?.set(key, Json.encodeToString(value))

inline fun <reified T> NavController.getArgsWhenBackPressed(
    key: String,
): T? = try {
    currentBackStackEntry?.savedStateHandle?.run {
        val result = get<String>(key).orEmpty()
        remove<String>(key)
        Json.decodeFromString(result)
    }
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun NavController.navigateTo(
    route: Any,
    popUpTo: Any? = null,
    inclusive: Boolean = false,
    saveState: Boolean = true,
    launchSingleTop: Boolean = false,
    restoreState: Boolean = true,
) {
    navigate(route) {
        popUpTo?.let {
            popUpTo(it) {
                this.inclusive = inclusive
                this.saveState = saveState
            }
        }
        this.launchSingleTop = launchSingleTop
        this.restoreState = restoreState
    }
}

fun NavController.navigateClearBackStack(route: Any) {
    navigate(route) {
        popUpTo(graph.id) {
            inclusive = false
            saveState = false
        }
        launchSingleTop = true
        restoreState = false
    }
}

fun buildDeepLink(uri: String) = navDeepLink { uriPattern = uri }
