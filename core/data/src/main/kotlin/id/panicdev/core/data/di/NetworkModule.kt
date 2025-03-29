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
package id.panicdev.core.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.panicdev.core.data.BuildConfig
import id.panicdev.core.data.domain.repository.UserRepository
import id.panicdev.core.data.domain.usecase.GetFollowersUseCase
import id.panicdev.core.data.domain.usecase.GetRepositoriesUseCase
import id.panicdev.core.data.domain.usecase.GetUserUseCase
import id.panicdev.core.data.domain.usecase.UserUseCases
import id.panicdev.core.data.remote.api.GitHubApiService
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val APPLICATION_JSON = "application/json"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
    ) = ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context)).build()

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val cache = Cache(
            directory = File(context.cacheDir, "http_cache"),
            maxSize = 10 * 1024 * 1024,
        )
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .addInterceptor(chuckerInterceptor)
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=${60 * 60}")
                    .build()
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): GitHubApiService {
        return return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(GitHubApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(
        repository: UserRepository,
    ): UserUseCases {
        return UserUseCases(
            getUserUseCase = GetUserUseCase(repository),
            getFollowersUseCase = GetFollowersUseCase(repository),
            getRepositoriesUseCase = GetRepositoriesUseCase(repository),
        )
    }
}
