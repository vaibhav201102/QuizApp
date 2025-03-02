package com.vaibhavjoshi.quizapp.data.repository

import com.vaibhavjoshi.quizapp.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface quizRepository {

    fun apiUrlRepository(
        url: String,
    ): Flow<NetworkResult<Response<ResponseBody>>>

}