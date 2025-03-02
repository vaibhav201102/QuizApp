package com.vaibhavjoshi.quizapp.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.FragmentResultBinding
import com.vaibhavjoshi.quizapp.view.activities.CategoryWiseQuizActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private var score : String = ""
    private var correctAnswers : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getString("SCORE",score)
            correctAnswers = it.getString("CORRECT_ANSWERS",correctAnswers)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        return view
    }

    private fun init(){
        (requireActivity() as CategoryWiseQuizActivity).initScoreActionbar(
            leftButtonText = "",
            rightButtonText = "",
            appbarTitle = "",
            isVisible = false
        )
        (requireActivity() as CategoryWiseQuizActivity).initActionbar("Result", isVisible = true)

        playAgainButton()
        setResultMessage(score, correctAnswers)
        onBackPressed()
    }

    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //TODO : SET PLAY AGAIN SCREEN
                openReplayScreen(Bundle())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun setResultMessage(score: String, correctAnswers: String){

        // Determine the result message and color based on correct answers
        val resultMessage = when (correctAnswers) {
            "10" -> {
//                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                "Awesome. You are Genius. Congratulations you won the Game."
            }
            "9" -> {
//                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                "You Won! Congratulations and Well Done."
            }
            "7", "8" -> {
//                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                "You Won! Congratulations."
            }
            "5", "6" -> {
//                playSound("win")
                binding.image.setImageResource(R.drawable.icon_winner)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                "You Won!"
            }
            "3", "4" -> {
//                playSound("lose")
                binding.image.setImageResource(R.drawable.icon_better_luck_next_time)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                "Well played but you failed. All The Best for Next Game."
            }
            else -> {
//                playSound("try_again")
                binding.image.setImageResource(R.drawable.try_again)
                binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                binding.tvTotalScore.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
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
            //TODO : REPLAY FUNCTIONALITY
            openReplayScreen(Bundle())
        }
    }

    private fun openReplayScreen(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()
        findNavController().navigate(R.id.letsPlayFragment, bundle, navOptions)
    }

}