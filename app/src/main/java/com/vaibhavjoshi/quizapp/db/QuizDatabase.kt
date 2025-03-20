package com.vaibhavjoshi.quizapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vaibhavjoshi.quizapp.dao.QuestionDao
import com.vaibhavjoshi.quizapp.model.Questions
import com.vaibhavjoshi.quizapp.utils.ListOfQuestions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Questions::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                    .addCallback(QuizDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class QuizDatabaseCallback(private val context: Context, private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(context, database.questionDao())
                }
            }
        }

        suspend fun populateDatabase(context: Context, questionDao: QuestionDao) {
            questionDao.insertQuestions(ListOfQuestions.questions)
        }
    }
}
