package com.vaibhavjoshi.quizapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.ActivityResultBinding

@SuppressLint("SetTextI18n")
class ResultActivity : AppCompatActivity() {

    // Variables
    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!

    // On Create Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Get score and correct answers count from the intent
        val score = intent.getIntExtra("SCORE", 0)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)

        setResultMessage(score, correctAnswers)
        playAgainButton()
    }

    // Set Result Data
    private fun setResultMessage(score: Int, correctAnswers: Int){

        // Determine the result message and color based on correct answers
        val resultMessage = when (correctAnswers) {
            10 -> {
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "Awesome. You are Genius. Congratulations you won the Game."
            }
            9 -> {
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "You Won! Congratulations and Well Done."
            }
            7, 8 -> {
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "You Won! Congratulations."
            }
            5, 6 -> {
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "You Won!"
            }
            3, 4 -> {
                binding.image.setImageResource(R.drawable.icon_better_luck_next_time)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.red))
                "Well played but you failed. All The Best for Next Game."
            }
            else -> {
                binding.image.setImageResource(R.drawable.try_again)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.red))
                "Sorry, You failed."
            }
        }

        // Set the result message text
        binding.tvResultMessage.text = resultMessage

        // Set the total score text
        binding.tvTotalScore.text = "Total Score: $score"

    }

    // Set up the play again button
    private fun playAgainButton(){
        binding.btnPlayAgain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}