package com.rago.league.data.di

import android.content.Context
import com.rago.league.data.api.TeamApiService
import com.rago.league.utils.Constant
import com.rago.league.utils.NetworkUtils
import com.rago.league.utils.RetrofitUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): TeamApiService =
        retrofit.create(TeamApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context) = NetworkUtils(context)

    @Provides
    @Singleton
    fun provideRetrofitUtils(
        networkUtils: NetworkUtils
    ) = RetrofitUtils(networkUtils)
}