package com.vaibhavjoshi.quizapp.di

import android.annotation.SuppressLint
import com.vaibhavjoshi.quizapp.data.api.Api
import com.vaibhavjoshi.quizapp.utils.Constants.baseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    val timeout : Long = 60
    @Singleton
    @Provides
    fun providesRetrofit()  :   Retrofit{
        val interceptor     =   HttpLoggingInterceptor()
        interceptor.level   =   HttpLoggingInterceptor.Level.BODY

        val httpBuilder     =   OkHttpClient.Builder()
//                                    .addInterceptor(OAuthInterceptor("---ACCESS---TOKEN---"))

        httpBuilder
            .addInterceptor(interceptor)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .callTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
        val mClient         =   httpBuilder.build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(mClient)
            .build()
    }
    @SuppressLint("SuspiciousIndentation")
    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor()     :    HttpLoggingInterceptor {
        val interceptor     =   HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor  : HttpLoggingInterceptor)   : OkHttpClient {
        return OkHttpClient
            .Builder()
            .callTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout,TimeUnit.SECONDS)
            .writeTimeout(timeout,TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesQuizApi(retrofit    : Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }

    /*    class OAuthInterceptor(private var acceessToken : String)    : Interceptor {

            override fun intercept(chain    : Interceptor.Chain)    : okhttp3.Response {
                acceessToken    =   ""
                var request     =   chain.request()
                request         =   request.newBuilder()
                                        .header("Authorization", "Bearer $acceessToken")
                                        .build()

                return chain.proceed(request)
            }
        }*/
}