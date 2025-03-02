package com.vaibhavjoshi.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavjoshi.quizapp.data.repository.quizRepository
import com.vaibhavjoshi.quizapp.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val dairyRepository: quizRepository) :
    ViewModel() {
    fun apiService(
        apiUrl: String,
        onLoading: (String) -> Unit,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
    ) {

        viewModelScope.launch {
            try {
                dairyRepository.apiUrlRepository(apiUrl).collectLatest { response ->
                    withContext(Dispatchers.Main) {
                        when (response) {

                            is NetworkResult.Error -> {
                                Log.i("==>", "ERROR" + " " + response.message)
                                // Method to Save Request Response and Api Url into Firebase RealTime Database
                                //activity.saveDataToFirebaseDatabase(apiUrl,"$BASEURL$apiUrl", requestMap.toString(),response.toString() )
                                onFailure(response.message.toString())
                            }

                            is NetworkResult.Loading -> {
                                Log.i("==>", "LOADING" + " " + response.message)
                                onLoading(response.message.toString())
                            }

                            is NetworkResult.Success -> {

                                if (response.data?.isSuccessful == true) {
                                    val responseBodyString = response.data.body()?.string()
                                    val jsonResponse = JSONObject(responseBodyString.toString())
//                                    val statusCode = jsonResponse.getString("StatusCode")
                                    // Method to Save Request Response and Api Url into Firebase RealTime Database
                                    //activity.saveDataToFirebaseDatabase(apiUrl,"$BASEURL$apiUrl", requestMap.toString(),responseBodyString.toString() )

                                    onSuccess(jsonResponse.toString())

                                    /*if (statusCode.equals("200")) {
                                        onSuccess(responseBodyString.toString())
                                    } else if (statusCode.equals("401")) {
                                        //TODO : SET FAILED RESPONSE
                                    } else {
                                        onSuccess(responseBodyString.toString())
                                    }*/
                                    Log.i("==>", "SUCCESS")
                                    // Handle the JSON string as needed
                                } else {
                                    // Method to Save Request Response and Api Url into Firebase RealTime Database
                                    //activity.saveDataToFirebaseDatabase(apiUrl,"$BASEURL$apiUrl", requestMap.toString(),response.message.toString() )
                                    Log.i("==>", "ERROR" + response.message)
                                    onFailure(response.message.toString())
                                    // Handle error
                                }
                            }

                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("==>", "Exception: ${e.message}")
            }
        }

    }

//    private val _score = MutableLiveData(0)
//    val score: LiveData<Int> get() = _score
//
//    fun updateScore(newScore: Int) {
//        _score.value = newScore
//    }
//
    private val _apiSessionToken = MutableLiveData("")
    val apiSessionToken: LiveData<String> get() = _apiSessionToken

    fun updateSessionToken(token: String) {
        _apiSessionToken.value = token
    }

    private val _score = MutableLiveData(0)

    private val _timer = MutableLiveData(0)

    private val _questionIndex = MutableLiveData(1)

    // Combine Score, Timer, and QuestionIndex into one LiveData
    val combinedData = MediatorLiveData<Triple<Int, Int, Int>>().apply {
        addSource(_score) { score -> value = Triple(score, _timer.value ?: 0, _questionIndex.value ?: 1) }
        addSource(_timer) { timer -> value = Triple(_score.value ?: 0, timer, _questionIndex.value ?: 1) }
        addSource(_questionIndex) { index -> value = Triple(_score.value ?: 0, _timer.value ?: 0, index) }
    }

    fun updateScore(newScore: Int) {
        _score.value = newScore
    }

    fun updateTimer(newTimer: Int) {
        _timer.value = newTimer
    }

    fun updateQuestionIndex(newIndex: Int) {
        _questionIndex.value = newIndex
    }




}

