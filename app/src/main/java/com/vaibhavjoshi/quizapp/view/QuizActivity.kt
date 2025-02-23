package com.vaibhavjoshi.quizapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SoundEffectConstants
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.ActivityQuizBinding
import com.vaibhavjoshi.quizapp.db.QuizDatabaseHelper
import com.vaibhavjoshi.quizapp.model.Question
import com.vaibhavjoshi.quizapp.model.Questions
import com.vaibhavjoshi.quizapp.viewmodel.QuestionViewModel

@SuppressLint("Range","SetTextI18n")
class QuizActivity : AppCompatActivity() {

    // Variables
    private var _binding: ActivityQuizBinding? = null
    private val binding get() = _binding!!
    private var score = 0
    private var questionIndex = 0
    private val questions = mutableListOf<Questions>()
    private var timer: CountDownTimer? = null
    private var correctAnswersCount = 0
    private var timerTime = 20

    private val questionViewModel: QuestionViewModel by viewModels()

    // On Create Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.btnOption1.playSoundEffect(SoundEffectConstants.CLICK)
        binding.btnOption2.playSoundEffect(SoundEffectConstants.CLICK)
        binding.btnOption3.playSoundEffect(SoundEffectConstants.CLICK)
        
        loadQuestions()
    }

    private fun loadQuestions(){
        questionViewModel.questions.observe(this) { list ->
            if (list != null && list.isNotEmpty()) {
                Log.d("UI", "Received ${list.size} questions")
                questions.addAll(list)
                showNextQuestion()
            } else {
                Log.d("UI", "Question list is empty")
            }
        }

        // Fetch data after observer is set
        questionViewModel.loadQuestions()
    }

    // Show the next question
    private fun showNextQuestion() {
        binding.btnOption1.isClickable = true
        binding.btnOption2.isClickable = true
        binding.btnOption3.isClickable = true
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
        binding.btnOption1.isClickable = false
        binding.btnOption2.isClickable = false
        binding.btnOption3.isClickable = false
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
        }, 2000)
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
