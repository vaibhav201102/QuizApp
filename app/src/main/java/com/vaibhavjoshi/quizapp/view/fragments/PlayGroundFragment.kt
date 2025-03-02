package com.vaibhavjoshi.quizapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.FragmentPlayGroundBinding
import com.vaibhavjoshi.quizapp.databinding.FragmentSelectTypeBinding
import com.vaibhavjoshi.quizapp.model.ApiQuestion
import com.vaibhavjoshi.quizapp.model.ApiResponse
import com.vaibhavjoshi.quizapp.model.Questions
import com.vaibhavjoshi.quizapp.utils.AppHelper
import com.vaibhavjoshi.quizapp.view.activities.CategoryWiseQuizActivity
import com.vaibhavjoshi.quizapp.view.activities.ResultActivity
import com.vaibhavjoshi.quizapp.view.adapter.SelectGameAdapter
import com.vaibhavjoshi.quizapp.view.adapter.SelectOptionsAdapter
import com.vaibhavjoshi.quizapp.viewmodel.QuestionViewModel
import com.vaibhavjoshi.quizapp.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.Locale

@AndroidEntryPoint
class PlayGroundFragment : Fragment() {

    private var _binding: FragmentPlayGroundBinding? = null
    private val binding get() = _binding!!
    private var selectOptionsAdapter = SelectOptionsAdapter()
    private var score = 0
    private var questionIndex = 0
    private val questions = arrayListOf<ApiQuestion>()
    private var timer: CountDownTimer? = null
    private var correctAnswersCount = 0
    private var timerTime = 20
    private val sharedViewModel : SharedViewModel by viewModels()

    private var selectedGameCategory = ""
    private var selectedGameDifficulty = ""
    private var selectedGameType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedGameCategory = it.getString("selectedGameCategory",selectedGameCategory)
            selectedGameDifficulty = it.getString("selectedDifficulty",selectedGameCategory)
            selectedGameType = it.getString("selectedType",selectedGameType)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentPlayGroundBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        return view
    }

    private fun init(){
        (requireActivity() as CategoryWiseQuizActivity).initActionbar("", isVisible = false)

        serviceGetQuestionsWithOptionsApi()

        sharedViewModel.combinedData.observe(viewLifecycleOwner) { (updatedScore, updatedTimer, updatedQuestionIndex) ->
            (requireActivity() as CategoryWiseQuizActivity).initScoreActionbar(
                leftButtonText = "Timer: $updatedTimer",
                rightButtonText = "Score: $updatedScore",
                appbarTitle = "Question: $updatedQuestionIndex",
                isVisible = true
            )
        }

        onBackPressed()
        initOptionsListing()
    }

    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openReplayScreen(Bundle())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun initOptionsListing() {

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        val recyclerViewScanItemList = binding.optionsContainer
        recyclerViewScanItemList.layoutManager = layoutManager

        selectOptionsAdapter = SelectOptionsAdapter()
        selectOptionsAdapter.onItemClick = {

        }

        recyclerViewScanItemList.adapter = selectOptionsAdapter

    }

    // Show the next question
    private fun showNextQuestion() {
        if (questionIndex < questions.size) {
            val currentQuestion = questions[questionIndex]

            // Set question text
            binding.tvQuestion.text = currentQuestion.question.toString()
            sharedViewModel.updateQuestionIndex(questionIndex + 1)

            // Create an option list with correct and incorrect answers
            val optionsList = ArrayList<String>().apply {
                add(currentQuestion.correct_answer)
                addAll(currentQuestion.incorrect_answers)
                shuffle()
            }

            // Set shuffled options to buttons
            selectOptionsAdapter.updateItems(optionsList)

            selectOptionsAdapter.onItemClick = { selectedOption ->
                checkAnswer(selectedOption, currentQuestion.correct_answer)
            }

            selectOptionsAdapter.resetColors()
            startTimer()
        } else {
            finishQuiz()
        }
    }

    private fun checkAnswer(selectedOption: String, correctAnswer: String) {
        timer?.cancel()

        if (selectedOption == correctAnswer) {
            correctAnswersCount++
            score += 10 + 20 - timerTime
            sharedViewModel.updateScore(score)
        }

        // Update adapter to show correct/incorrect answers
        selectOptionsAdapter.updateAnswer(selectedOption, correctAnswer)

        // Move to the next question after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            questionIndex++
            showNextQuestion()
        }, 2000)
    }

    // Start the timer
    private fun startTimer() {
        selectOptionsAdapter.resetColors()

        timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTime = (millisUntilFinished / 1000).toInt()
                sharedViewModel.updateTimer(timerTime)
            }

            override fun onFinish() {
                questionIndex++
                showNextQuestion()
            }
        }.start()
    }

    // Finish the quiz
    private fun finishQuiz() {

        val args = Bundle()
        args.putString("SCORE", score.toString())
        args.putString("CORRECT_ANSWERS", correctAnswersCount.toString())
        openResultScreen(args)

    }

    private fun openResultScreen(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()
        findNavController().navigate(R.id.resultFragment, bundle, navOptions)
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

    private fun serviceGetQuestionsWithOptionsApi(){
        val category : String = when(selectedGameCategory){
//            "Any Category" -> "any"
            "General Knowledge" -> "9"
            "Entertainment: Books" -> "10"
            "Entertainment: Film" -> "11"
            "Entertainment: Music" -> "12"
            "Entertainment: Musicals & Theatres" -> "13"
            "Entertainment: Television" -> "14"
            "Entertainment: Video Games" -> "15"
            "Entertainment: Board Games" -> "16"
            "Science & Nature" -> "17"
            "Science: Computers" -> "18"
            "Science: Mathematics" -> "19"
            "Mythology" -> "20"
            "Sports" -> "21"
            "Geography" -> "22"
            "History" -> "23"
            "Politics" -> "24"
            "Art" -> "25"
            "Celebrities" -> "26"
            "Animals" -> "27"
            "Vehicles" -> "28"
            "Entertainment: Comics" -> "29"
            "Science: Gadgets" -> "30"
            "Entertainment: Japanese Anime & Manga" -> "31"
            "Entertainment: Cartoon & Animations" -> "32"
            else -> "any"
        }
        val questionCount = "10"
        val difficulty = if (selectedGameType.equals("Multiple Choice",true)) "multiple" else "boolean"
        val apiUrl =
            if (sharedViewModel.apiSessionToken.value?.isEmpty() == true)
                "api.php?amount=" + questionCount + "&category=" + category + "&difficulty=" + selectedGameDifficulty.lowercase(Locale.getDefault()) + "&type=" + difficulty
            else
                "api.php?amount=" + questionCount + "&category=" + category + "&difficulty=" + selectedGameDifficulty.lowercase(Locale.getDefault()) + "&type=" + difficulty + "&token=" + sharedViewModel.apiSessionToken.value

        sharedViewModel.apiService(
            apiUrl = apiUrl,
            onLoading = {

            },
            onSuccess = { response ->
                val quizApiResponse : ApiResponse? = AppHelper.convertJsonToModel(response)
                println(quizApiResponse)
                when (quizApiResponse?.responseCode){
                    "0" -> {
//                        Code 0: Success Returned results successfully.
                        val formattedQuestions = quizApiResponse.results.map { question ->
                            ApiQuestion(
                                question = question.question.toString(),
                                correct_answer = question.correctAnswer.toString(),
                                incorrect_answers = question.incorrectAnswers
                            )
                        }

                        questions.clear()
                        questions.addAll(formattedQuestions)
                        questionIndex = 0
                        showNextQuestion()
                    }
                    "1" -> {
//                        Code 1: No Results Could not return results. The API doesn't have enough questions for your query. (Ex. Asking for 50 Questions in a Category that only has 20.)
                    }
                    "2" -> {
//                        Code 2: Invalid Parameter Contains an invalid parameter. Arguements passed in aren't valid. (Ex. Amount = Five)
                    }
                    "3" -> {
//                        Code 3: Token Not Found Session Token does not exist.
                    }
                    "4" -> {
//                        Code 4: Token Empty Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.
                    }
                    "5" -> {
//                        Code 5: Rate Limit Too many requests have occurred. Each IP can only access the API once every 5 seconds.
                    }
                    else -> {}
                }

            },
            onFailure = {

            },
        )
    }
}