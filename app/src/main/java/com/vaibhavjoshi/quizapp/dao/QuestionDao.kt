package com.vaibhavjoshi.quizapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vaibhavjoshi.quizapp.model.Questions

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Questions>)

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions() : List<Questions>

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 10")
    suspend fun getRandomQuestions(): List<Questions>
}