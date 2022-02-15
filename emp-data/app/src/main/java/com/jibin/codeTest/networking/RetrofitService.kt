package com.jibin.codeTest.networking

import android.content.Context
import com.jibin.codeTest.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService(var context: Context) {


    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(context.cacheDir, cacheSize)

    private var moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private var interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder().apply {
        readTimeout(60, TimeUnit.SECONDS)
        cache(myCache)
        connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            addInterceptor(interceptor)
        }
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

            chain.proceed(request)
        }
    }.build()


    fun <S> createService(serviceClass: Class<S>): S {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(serviceClass)
    }
}