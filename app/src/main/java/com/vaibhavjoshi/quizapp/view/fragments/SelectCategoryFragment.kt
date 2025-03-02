package com.vaibhavjoshi.quizapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.FragmentSelectCategoryBinding
import com.vaibhavjoshi.quizapp.view.activities.CategoryWiseQuizActivity
import com.vaibhavjoshi.quizapp.view.adapter.SelectGameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get() = _binding!!
    private var selectGameAdapter = SelectGameAdapter()

    private var gameList: List<String> = mutableListOf(
//        "Any Category",
        "General Knowledge",
        "Entertainment: Books",
        "Entertainment: Film",
        "Entertainment: Music",
        "Entertainment: Musicals & Theatres",
        "Entertainment: Television",
        "Entertainment: Video Games",
        "Entertainment: Board Games",
        "Science & Nature",
        "Science: Computers",
        "Science: Mathematics",
        "Mythology",
        "Sports",
        "Geography",
        "History",
        "Politics",
        "Art",
        "Celebrities",
        "Animals",
        "Vehicles",
        "Entertainment: Comics",
        "Science: Gadgets",
        "Entertainment: Japanese Anime & Manga",
        "Entertainment: Cartoon & Animations"
    )

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
        _binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        return view
    }

    private fun init() {
        (requireActivity() as CategoryWiseQuizActivity).initScoreActionbar("","","", isVisible = false)

        (requireActivity() as CategoryWiseQuizActivity).initActionbar(
            "Select Game",
            leftButton = R.drawable.icon_back,
            rightButton = 0,
            leftButtonClick = {
                findNavController().popBackStack()
            }, isVisible = true)

        onBackPressed()
        initSelectGameListing()
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

    private fun initSelectGameListing() {

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(),2)
        val recyclerViewScanItemList = binding.selectGameRv
        recyclerViewScanItemList.layoutManager = layoutManager

        selectGameAdapter = SelectGameAdapter()
        selectGameAdapter.updateItems(gameList)
        selectGameAdapter.onItemClick = {
            val args = Bundle()
            args.putString("selectedGameCategory",it.toString())
            openTypeScreen(args)
        }

        recyclerViewScanItemList.adapter = selectGameAdapter

    }

    private fun openTypeScreen(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()
        findNavController().navigate(R.id.selectDifficultyFragment, bundle, navOptions)
    }
}