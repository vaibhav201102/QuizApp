package com.vaibhavjoshi.quizapp.db

import com.vaibhavjoshi.quizapp.dao.QuestionDao
import com.vaibhavjoshi.quizapp.model.Questions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun getAllQuestions(): List<Questions> {
        return withContext(Dispatchers.IO) {
            questionDao.getAllQuestions()
        }
    }

    suspend fun insertQuestions(questions: List<Questions>) {
        withContext(Dispatchers.IO) {
            questionDao.insertQuestions(questions)
        }
    }

    suspend fun getRandomQuestions(): List<Questions> {
        return questionDao.getRandomQuestions()
    }
}