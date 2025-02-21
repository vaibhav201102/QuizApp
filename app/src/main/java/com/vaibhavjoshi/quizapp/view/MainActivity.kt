package com.vaibhavjoshi.quizapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.vaibhavjoshi.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Variables
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // On Create Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        buttonPlayGame()
    }

    // Button Play Game
    private fun buttonPlayGame(){
        binding.btnPlayGame.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }
}