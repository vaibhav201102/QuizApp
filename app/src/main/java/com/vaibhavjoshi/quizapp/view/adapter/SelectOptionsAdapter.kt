package com.vaibhavjoshi.quizapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.LayoutOptionsBinding

@SuppressLint("NotifyDataSetChanged")
class SelectOptionsAdapter: RecyclerView.Adapter<SelectOptionsAdapter.ViewHolder>() {

    //region VARIABLES

    private var optionsList: List<String> = mutableListOf()
    private var selectedOption: String? = null
    private var correctAnswer: String? = null
    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(val binding: LayoutOptionsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(option: String) {
            binding.btnOption.text = option
            binding.btnOption.setBackgroundResource(R.drawable.btn_quiz_option)

            // Update colors if an answer was selected
            if (selectedOption != null) {
                when (option) {
                    correctAnswer -> {
                        binding.btnOption.setBackgroundResource(R.drawable.btn_correct_answer)
                    }
                    selectedOption -> {
                        binding.btnOption.setBackgroundResource(R.drawable.btn_incorrect_answer)
                    }
                }
            }else{
                binding.btnOption.setBackgroundResource(R.drawable.btn_quiz_option)
            }

            binding.btnOption.setOnClickListener {
                if (selectedOption == null) {
                    onItemClick?.invoke(option)
                }
            }
        }
    }

    //endregion VARIABLES

    //region OVERRIDE METHODS (LIFECYCLE)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = optionsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = optionsList[position]
        holder.bind(currentItem)
    }

    //endregion OVERRIDE METHODS (LIFECYCLE)

    //region ALL FUNCTIONS

    fun updateItems(items: List<String>?) {
        optionsList = items ?: emptyList()
        selectedOption = null
        correctAnswer = null
        notifyDataSetChanged()
    }

    fun updateAnswer(selected: String, correct: String) {
        selectedOption = selected
        correctAnswer = correct
        notifyDataSetChanged()
    }

    fun resetColors() {
        selectedOption = null
        correctAnswer = null
        notifyDataSetChanged()
    }
    //endregion ALL FUNCTIONS
}