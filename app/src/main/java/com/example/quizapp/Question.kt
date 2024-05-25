package com.example.quizapp

// Question class
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