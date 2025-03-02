package com.vaibhavjoshi.quizapp.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Url

@JvmSuppressWildcards
interface Api {

    @GET
    suspend fun apiUrl(@Url url: String) : Response<ResponseBody>

}