package com.vaibhavjoshi.quizapp.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.SoundEffectConstants
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
    private var mediaPlayer: MediaPlayer = MediaPlayer()

    // On Create Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.btnPlayAgain.playSoundEffect(SoundEffectConstants.CLICK)

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
                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "Awesome. You are Genius. Congratulations you won the Game."
            }
            9 -> {
                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "You Won! Congratulations and Well Done."
            }
            7, 8 -> {
                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "You Won! Congratulations."
            }
            5, 6 -> {
                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.green))
                "You Won!"
            }
            3, 4 -> {
                playSound("lose")
                binding.image.setImageResource(R.drawable.icon_better_luck_next_time)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(this, R.color.red))
                "Well played but you failed. All The Best for Next Game."
            }
            else -> {
                playSound("try_again")
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
            if (mediaPlayer.isPlaying) {

                mediaPlayer.release()

            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    // Remember to release the MediaPlayer when you no longer need it

    override fun onDestroy() {

        super.onDestroy()

        if (mediaPlayer.isPlaying) {

            mediaPlayer.release()

        }

    }

    private fun playSound(soundType: String) {
        val soundResId = when (soundType) {
            "win"       -> R.raw.winning_sound
            "lose"      -> R.raw.lossing_sound
            "try_again" -> R.raw.try_again_sound
            else -> return // If an unknown type is passed, do nothing
        }

        // Release existing MediaPlayer if it's already playing
        if (mediaPlayer != null){
            mediaPlayer.release()
        }
//        mediaPlayer.release()

        // Initialize and start playing the selected sound
        mediaPlayer = MediaPlayer.create(this@ResultActivity, soundResId)
        mediaPlayer.start()

        // Release MediaPlayer when sound is done playing
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

}