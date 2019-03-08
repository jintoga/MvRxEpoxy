package com.jintoga.mvrxepoxy.core

import com.jintoga.mvrxepoxy.network.DadJokeService
import com.jintoga.mvrxepoxy.network.EarningService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val apiServiceModule: Module = module {
    single {
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }
    single {
        MoshiConverterFactory.create(get())
    }
    single {
        OkHttpClient()
                .newBuilder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
    }
    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        logging
    }
    single("DadJokeAPI") {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://icanhazdadjoke.com/")
                .client(get())
                .addConverterFactory(get<MoshiConverterFactory>())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(DadJokeService::class.java)
    }
    single("EarningAPI") {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.earningscalendar.net/")
                .client(get())
                .addConverterFactory(get<MoshiConverterFactory>())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(EarningService::class.java)
    }
}
