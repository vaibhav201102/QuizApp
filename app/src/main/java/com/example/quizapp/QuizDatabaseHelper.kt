package com.example.quizapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuizDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Define the table and columns
    companion object {
        private const val DATABASE_NAME = "quiz.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME        = "questions"
        const val COLUMN_ID         = "id"
        const val COLUMN_QUESTION   = "question"
        const val COLUMN_OPTION1    = "option1"
        const val COLUMN_OPTION2    = "option2"
        const val COLUMN_OPTION3    = "option3"
        const val COLUMN_OPTION4    = "option4"
        const val COLUMN_ANSWER     = "answer"
    }

    // Create the questions table
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_QUESTION TEXT,"
                + "$COLUMN_OPTION1 TEXT,"
                + "$COLUMN_OPTION2 TEXT,"
                + "$COLUMN_OPTION3 TEXT,"
                + "$COLUMN_OPTION4 TEXT,"
                + "$COLUMN_ANSWER TEXT)"
                )
        db.execSQL(createTable)
        populateQuestions(db)
    }

    // Update the database schema if needed
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Populate the questions table with data
    private fun populateQuestions(db: SQLiteDatabase) {
        val questions = arrayOf(
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Who is the Prime Minister of India?', 'Narendra Modi', 'Rahul Gandhi', 'Manmohan Singh', 'Amit Shah', 'Narendra Modi')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is the capital of India?', 'Mumbai', 'Chennai', 'Delhi', 'Ahmedabad', 'Delhi')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is sum of 15 + 25?', '5', '25', '40', 'None', '40')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Which one is maximum? 25, 11, 17, 18, 40, 42', '11', '42', '17', 'None', '42')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is the official language of Gujarat?', 'Hindi', 'Gujarati', 'Marathi', 'None', 'Gujarati')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is multiplication of 12 * 12 ?', '124', '12', '24', 'None', 'None')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Which state of India has the largest population?', 'UP', 'Bihar', 'Gujarat', 'Maharashtra', 'UP')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Who is the Home Minister of India?', 'Amit Shah', 'Rajnath Singh', 'Narendra Modi', 'None', 'Amit Shah')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is the capital of Gujarat?', 'Vadodara', 'Ahmedabad', 'Gandhinagar', 'Rajkot', 'Gandhinagar')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Which number will be next in series? 1, 4, 9, 16, 25', '21', '36', '49', '32', '36')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Which one is minimum? 5, 0, -20, 11', '0', '11', '-20', 'None', '-20')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is sum of 10, 12 and 15?', '37', '25', '10', '12', '37')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('What is the official language of the Government of India?', 'Hindi', 'English', 'Gujarati', 'None', 'Hindi')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Which country is located in Asia?', 'India', 'USA', 'UK', 'None', 'India')",
            "INSERT INTO $TABLE_NAME ($COLUMN_QUESTION, $COLUMN_OPTION1, $COLUMN_OPTION2, $COLUMN_OPTION3, $COLUMN_OPTION4, $COLUMN_ANSWER) VALUES ('Which language(s) is/are used for Android app development?', 'Java', 'Java & Kotlin', 'Kotlin', 'Swift', 'Java & Kotlin')",
        )
        for (question in questions) {
            db.execSQL(question)
        }
    }
}