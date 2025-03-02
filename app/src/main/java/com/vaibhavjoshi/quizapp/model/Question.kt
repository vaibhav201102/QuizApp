package com.vaibhavjoshi.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Question class

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: String
) {
    // Function to get random options for the question
    fun getRandomOptions(): List<String> {
        val options = mutableListOf(option1, option2, option3, option4).shuffled()
        val incorrectOptions = options.filter { it != answer }.shuffled().take(2)
        return (incorrectOptions + answer).shuffled()
    }
}

// Using Sqlite
data class Question(
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: String
) {
    // Function to get random options for the question
    fun getRandomOptions(): List<String> {
        val options = listOf(option1, option2, option3, option4)
        val incorrectOptions = options.filter { it != answer }.shuffled().take(2)
        return (incorrectOptions + answer).shuffled()
    }
}

// Using Api Service
data class ApiQuestion(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
