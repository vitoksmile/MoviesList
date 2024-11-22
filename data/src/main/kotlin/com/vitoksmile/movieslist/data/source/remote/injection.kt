package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteDataSourceModule {

    @Provides
    fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    fun providesOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()
    }

    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .client(client)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create<MoviesApi>()
    }
}
