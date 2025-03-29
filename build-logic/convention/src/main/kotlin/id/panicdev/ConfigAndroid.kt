package id.panicdev

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val javaVersion = libs.findVersion("java").get().toString()
    val minSdkVersion = libs.findVersion("minSdk").get().toString().toInt()
    val compileSdkVersion = libs.findVersion("compileSdk").get().toString().toInt()

    commonExtension.apply {
        compileSdk = compileSdkVersion

        defaultConfig {
            minSdk = minSdkVersion
        }

        buildFeatures {
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
            targetCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(javaVersion))
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            )
        }
    }
}