package com.vaibhavjoshi.quizapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.FragmentLetsPlayBinding
import com.vaibhavjoshi.quizapp.view.activities.CategoryWiseQuizActivity
import com.vaibhavjoshi.quizapp.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class LetsPlayFragment : Fragment() {

    private var _binding: FragmentLetsPlayBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding    = FragmentLetsPlayBinding.inflate(inflater, container, false)
        val view    = binding.root

        init()
        return view
    }

    private fun init(){
        (requireActivity() as CategoryWiseQuizActivity).initScoreActionbar("","","", isVisible = false)

        (requireActivity() as CategoryWiseQuizActivity).initActionbar("Welcome", leftButton = 0, rightButton = 0, isVisible = true)
        serviceGetApiTokenFromServerApi()
        playButtonClick()
        onBackPressed()
    }

    private fun playButtonClick(){
        binding.playButton.setOnClickListener{
            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.fade_out)
                .build()
        findNavController().navigate(R.id.selectCategoryFragment,Bundle(), navOptions)
        }
    }

    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                (requireActivity() as CategoryWiseQuizActivity).finish()
                (requireActivity() as CategoryWiseQuizActivity).finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun serviceGetApiTokenFromServerApi(){
        val apiUrl = "api_token.php?command=request"

        sharedViewModel.apiService(
            apiUrl = apiUrl,
            onLoading = {

            },
            onSuccess = { response ->

                val jsonResponse = JSONObject(response)
                val responseCode     = jsonResponse.getString("response_code")
                val responseMessage      = jsonResponse.getString("response_message")
                val token  = jsonResponse.getString("token")

                if (token.isNotEmpty()){
                    sharedViewModel.updateSessionToken(token)
                }
                else{
                    sharedViewModel.updateSessionToken("")
                }

            },
            onFailure = {

            },
        )
    }
}