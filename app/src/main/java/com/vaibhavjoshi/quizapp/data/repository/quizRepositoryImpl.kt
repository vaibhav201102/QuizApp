package com.vaibhavjoshi.quizapp.data.repository

import com.vaibhavjoshi.quizapp.data.api.Api
import com.vaibhavjoshi.quizapp.network.NetworkCall
import com.vaibhavjoshi.quizapp.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class quizRepositoryImpl @Inject constructor(private val api: Api) : quizRepository {

    override fun apiUrlRepository(
        url: String,
    ): Flow<NetworkResult<Response<ResponseBody>>> = flow {
        emit(NetworkResult.Loading())
        val response = NetworkCall.safeApiCall { api.apiUrl(url) }
        emit(response)
    }


}