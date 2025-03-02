package com.vaibhavjoshi.quizapp.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("response_code" ) var responseCode : String?               = null,
    @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf()
){
    data class Results (

        @SerializedName("type"              ) var type             : String?           = null,
        @SerializedName("difficulty"        ) var difficulty       : String?           = null,
        @SerializedName("category"          ) var category         : String?           = null,
        @SerializedName("question"          ) var question         : String?           = null,
        @SerializedName("correct_answer"    ) var correctAnswer    : String?           = null,
        @SerializedName("incorrect_answers" ) var incorrectAnswers : ArrayList<String> = arrayListOf()

    )
}
