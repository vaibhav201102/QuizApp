package com.vaibhavjoshi.quizapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.FragmentSelectDifficultyBinding
import com.vaibhavjoshi.quizapp.view.activities.CategoryWiseQuizActivity
import com.vaibhavjoshi.quizapp.view.adapter.SelectGameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDifficultyFragment : Fragment() {

    private var _binding: FragmentSelectDifficultyBinding? = null
    private val binding get() = _binding!!
    private var selectDifficultyAdapter = SelectGameAdapter()

    private var difficultyList: List<String> = mutableListOf(
        "Easy",
        "Medium",
        "Hard",
    )

    private var selectedGameCategory : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedGameCategory = it.getString("selectedGameCategory",selectedGameCategory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSelectDifficultyBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        return view
    }

    private fun init(){
        (requireActivity() as CategoryWiseQuizActivity).initScoreActionbar("","","", isVisible = false)
        (requireActivity() as CategoryWiseQuizActivity).initActionbar(
            "Select Difficulty",
            leftButton = R.drawable.icon_back,
            rightButton = 0,
            leftButtonClick = {
                findNavController().popBackStack()
            }, isVisible = true)

        initSelectDifficultyListing()
        onBackPressed()
    }

    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun initSelectDifficultyListing() {

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(),2)
        val recyclerViewScanItemList = binding.selectDifficultyRv
        recyclerViewScanItemList.layoutManager = layoutManager

        selectDifficultyAdapter = SelectGameAdapter()
        selectDifficultyAdapter.updateItems(difficultyList)
        selectDifficultyAdapter.onItemClick = {
            val args = Bundle()
            args.putString("selectedGameCategory",selectedGameCategory)
            args.putString("selectedDifficulty",it)
            openDifficultyScreen(args)

        }

        recyclerViewScanItemList.adapter = selectDifficultyAdapter

    }

    private fun openDifficultyScreen(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()
        findNavController().navigate(R.id.selectTypeFragment, bundle, navOptions)
    }
}