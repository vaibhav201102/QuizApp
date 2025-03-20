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
import com.vaibhavjoshi.quizapp.databinding.FragmentSelectTypeBinding
import com.vaibhavjoshi.quizapp.view.activities.CategoryWiseQuizActivity
import com.vaibhavjoshi.quizapp.view.adapter.SelectGameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectTypeFragment : Fragment() {

    private var _binding: FragmentSelectTypeBinding? = null
    private val binding get() = _binding!!
    private var selectGameTypeAdapter = SelectGameAdapter()

    private var gameTypeList: List<String> = mutableListOf(
        "Multiple Choice",
        "True / False",
    )
    private var selectedGameCategory : String = ""
    private var selectedGameDifficulty : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedGameCategory = it.getString("selectedGameCategory",selectedGameCategory)
            selectedGameDifficulty = it.getString("selectedDifficulty",selectedGameDifficulty)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentSelectTypeBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        return view
    }

    private fun init(){
        (requireActivity() as CategoryWiseQuizActivity).initScoreActionbar("","","", isVisible = false)

        (requireActivity() as CategoryWiseQuizActivity).initActionbar(
            "Select Type",
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
        val recyclerViewScanItemList = binding.selectGameTypeRv
        recyclerViewScanItemList.layoutManager = layoutManager

        selectGameTypeAdapter = SelectGameAdapter()
        selectGameTypeAdapter.updateItems(gameTypeList)
        selectGameTypeAdapter.onItemClick = {
            val args = Bundle()
            args.putString("selectedGameCategory",selectedGameCategory)
            args.putString("selectedDifficulty",selectedGameDifficulty)
            args.putString("selectedType",it)
            openPlaygroundScreen(args)

        }

        recyclerViewScanItemList.adapter = selectGameTypeAdapter

    }

    private fun openPlaygroundScreen(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()
        findNavController().navigate(R.id.playGroundFragment, bundle, navOptions)
    }

}