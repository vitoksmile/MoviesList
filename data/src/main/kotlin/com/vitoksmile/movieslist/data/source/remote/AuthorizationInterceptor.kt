package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.AUTHORIZATION}")
            .build()
        return chain.proceed(request)
    }
}
