package com.makeupapp.makeup.product.di

import com.makeupapp.makeup.common.Constants.BASE_URL
import com.makeupapp.makeup.product.network.NetworkInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

//    provides retrofit instance
    @Provides
    @Singleton
    fun providesRetrofitInstance(baseUrl: String): Retrofit{
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor = OkHttpClient.Builder().addInterceptor(logger).build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(interceptor)

        return retrofit.build()
    }

    @Provides
    @Singleton
    fun provideNetworkModuleApi(): NetworkInterface {
        return providesRetrofitInstance(BASE_URL).create(NetworkInterface::class.java)
    }

}