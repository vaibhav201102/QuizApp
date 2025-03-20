package com.vaibhavjoshi.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vaibhavjoshi.quizapp.db.QuestionRepository
import com.vaibhavjoshi.quizapp.db.QuizDatabase
import com.vaibhavjoshi.quizapp.model.Questions
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuestionRepository
    private val _questions = MutableLiveData<List<Questions>>()
    val questions: MutableLiveData<List<Questions>> get() = _questions

    init {
        val database = QuizDatabase.getDatabase(application, viewModelScope)
        val questionDao = database.questionDao()
        repository = QuestionRepository(questionDao)
        loadQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch {
            _questions.postValue(repository.getAllQuestions())
        }
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _questions.postValue(repository.getRandomQuestions())
        }
    }
}