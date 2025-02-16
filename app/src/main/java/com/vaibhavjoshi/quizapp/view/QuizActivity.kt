package com.vaibhavjoshi.quizapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.ActivityQuizBinding
import com.vaibhavjoshi.quizapp.db.QuizDatabaseHelper
import com.vaibhavjoshi.quizapp.model.Question

@SuppressLint("Range","SetTextI18n")
class QuizActivity : AppCompatActivity() {

    // Variables
    private var _binding: ActivityQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: QuizDatabaseHelper
    private lateinit var cursor: Cursor
    private var score = 0
    private var questionIndex = 0
    private val questions = mutableListOf<Question>()
    private var timer: CountDownTimer? = null
    private var correctAnswersCount = 0
    private var timerTime = 20

    // On Create Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dbHelper = QuizDatabaseHelper(this)
        loadQuestions()
        showNextQuestion()
    }

    // Load questions from the database
    private fun loadQuestions() {
        val db = dbHelper.readableDatabase
        cursor = db.rawQuery("SELECT * FROM ${QuizDatabaseHelper.TABLE_NAME} ORDER BY RANDOM() LIMIT 10", null)
        if (cursor.moveToFirst()) {
            do {
                val question = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_QUESTION))
                val option1 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_OPTION1))
                val option2 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_OPTION2))
                val option3 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_OPTION3))
                val option4 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_OPTION4))
                val answer = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_ANSWER))
                questions.add(Question(question, option1, option2, option3, option4, answer))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    // Show the next question
    private fun showNextQuestion() {
        if (questionIndex < questions.size) {
            val currentQuestion = questions[questionIndex]
            binding.tvQuestion.text = currentQuestion.question
            binding.tvQuestionNo.text = "Question: ${questionIndex + 1}"
            val options = currentQuestion.getRandomOptions()
            binding.btnOption1.text = options[0]
            binding.btnOption2.text = options[1]
            binding.btnOption3.text = options[2]

            resetButtonColors()

            binding.btnOption1.setOnClickListener { checkAnswer(binding.btnOption1.text.toString(), currentQuestion.answer, binding.btnOption1) }
            binding.btnOption2.setOnClickListener { checkAnswer(binding.btnOption2.text.toString(), currentQuestion.answer, binding.btnOption2) }
            binding.btnOption3.setOnClickListener { checkAnswer(binding.btnOption3.text.toString(), currentQuestion.answer, binding.btnOption3) }

            startTimer()
        } else {
            finishQuiz()
        }
    }

    // Reset button colors and timer
    private fun resetButtonColors() {

        binding.btnOption1.setBackgroundResource(R.drawable.style_btn_corner)
        binding.btnOption2.setBackgroundResource(R.drawable.style_btn_corner)
        binding.btnOption3.setBackgroundResource(R.drawable.style_btn_corner)

        binding.btnOption1.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_blue_light)
        binding.btnOption2.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_blue_light)
        binding.btnOption3.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_blue_light)
    }

    // Check the selected option and update the UI
    private fun checkAnswer(selectedOption: String, correctAnswer: String, selectedButton: Button) {
        timer?.cancel()
        if (selectedOption == correctAnswer) {
            correctAnswersCount++
            score += 10 + 20 - timerTime
            binding.tvScore.text = "Score: $score"
            selectedButton.setBackgroundResource(R.drawable.style_btn_corner)
            selectedButton.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_green_light)
        } else {
            selectedButton.setBackgroundResource(R.drawable.style_btn_corner)
            selectedButton.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_red_light)
            highlightCorrectAnswer(correctAnswer)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            questionIndex++
            showNextQuestion()
        }, 200)
    }

    // Highlight the correct answer
    private fun highlightCorrectAnswer(correctAnswer: String) {
        when (correctAnswer) {
            binding.btnOption1.text.toString() -> {
                binding.btnOption1.setBackgroundResource(R.drawable.style_btn_corner)
                binding.btnOption1.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_green_light)
            }
            binding.btnOption2.text.toString() -> {
                binding.btnOption2.setBackgroundResource(R.drawable.style_btn_corner)
                binding.btnOption2.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_green_light)
            }
            binding.btnOption3.text.toString() -> {
                binding.btnOption3.setBackgroundResource(R.drawable.style_btn_corner)
                binding.btnOption3.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, android.R.color.holo_green_light)
            }
        }
    }

    // Start the timer
    private fun startTimer() {
        resetButtonColors()

        timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTime = (millisUntilFinished / 1000).toInt()
                binding.tvTimer.text = "Time: ${timerTime}s"
            }

            override fun onFinish() {
                questionIndex++
                showNextQuestion()
            }
        }.start()
    }

    // Finish the quiz
    private fun finishQuiz() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("SCORE", score)
        intent.putExtra("CORRECT_ANSWERS", correctAnswersCount)
        startActivity(intent)
        finish()
    }
}
