package com.vaibhavjoshi.quizapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.quizapp.R
import com.vaibhavjoshi.quizapp.databinding.LayoutGameSelectItemBinding
import java.util.Locale

@SuppressLint("NotifyDataSetChanged")
class SelectGameAdapter: RecyclerView.Adapter<SelectGameAdapter.ViewHolder>(), Filterable {

    //region VARIABLES

    private var hostList: List<String> = mutableListOf()
    private var filterList: List<String> = mutableListOf()
    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(val binding: LayoutGameSelectItemBinding) : RecyclerView.ViewHolder(binding.root)

    //endregion VARIABLES

    //region OVERRIDE METHODS (LIFECYCLE)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutGameSelectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filterList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = filterList[position]

        with(holder) {
            holder.binding.apply {
                itemText.text = currentItem.toString()
                itemView.setOnClickListener {
                    onItemClick?.invoke(currentItem)
                }
                when(currentItem){
                    "General Knowledge" -> binding.itemImage.setImageResource(R.drawable.ic_gk)
                    "Entertainment: Books" -> binding.itemImage.setImageResource(R.drawable.ic_books)
                    "Entertainment: Film" -> binding.itemImage.setImageResource(R.drawable.ic_film)
                    "Entertainment: Music" -> binding.itemImage.setImageResource(R.drawable.ic_music)
                    "Entertainment: Musicals & Theatres" -> binding.itemImage.setImageResource(R.drawable.ic_theatre)
                    "Entertainment: Television" -> binding.itemImage.setImageResource(R.drawable.ic_television)
                    "Entertainment: Video Games" -> binding.itemImage.setImageResource(R.drawable.ic_video_games)
                    "Entertainment: Board Games" -> binding.itemImage.setImageResource(R.drawable.ic_board_games)
                    "Science & Nature" -> binding.itemImage.setImageResource(R.drawable.ic_science_nature)
                    "Science: Computers" -> binding.itemImage.setImageResource(R.drawable.ic_computers)
                    "Science: Mathematics" -> binding.itemImage.setImageResource(R.drawable.ic_maths)
                    "Mythology" -> binding.itemImage.setImageResource(R.drawable.ic_mythology)
                    "Sports" -> binding.itemImage.setImageResource(R.drawable.ic_sports)
                    "Geography" -> binding.itemImage.setImageResource(R.drawable.ic_geography)
                    "History" -> binding.itemImage.setImageResource(R.drawable.ic_history)
                    "Politics" -> binding.itemImage.setImageResource(R.drawable.ic_politics)
                    "Art" -> binding.itemImage.setImageResource(R.drawable.ic_art)
                    "Celebrities" -> binding.itemImage.setImageResource(R.drawable.ic_celeb)
                    "Animals" -> binding.itemImage.setImageResource(R.drawable.ic_animals)
                    "Vehicles" -> binding.itemImage.setImageResource(R.drawable.ic_vehicles)
                    "Entertainment: Comics" -> binding.itemImage.setImageResource(R.drawable.ic_comics)
                    "Science: Gadgets" -> binding.itemImage.setImageResource(R.drawable.ic_gadgets)
                    "Entertainment: Japanese Anime & Manga" -> binding.itemImage.setImageResource(R.drawable.ic_anime)
                    "Entertainment: Cartoon & Animations" -> binding.itemImage.setImageResource(R.drawable.ic_cartoon)
                    "Easy" -> binding.itemImage.setImageResource(R.drawable.ic_easy)
                    "Medium" -> binding.itemImage.setImageResource(R.drawable.ic_medium)
                    "Hard" -> binding.itemImage.setImageResource(R.drawable.ic_hard)
                    "Multiple Choice" -> binding.itemImage.setImageResource(R.drawable.ic_multiple_choice)
                    "True / False" -> binding.itemImage.setImageResource(R.drawable.ic_true_false)
                }
            }

        }
    }

    //CATEGORY SEARCH FILTER FUNCTIONALITY
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = hostList.filter { item ->
                    item.toString().lowercase(Locale.getDefault()).contains(constraint.toString().lowercase(Locale.getDefault()))
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.values is List<*>) {
                    filterList = results.values as List<String>
                    notifyDataSetChanged()
                }
            }
        }
    }
    //endregion OVERRIDE METHODS (LIFECYCLE)

    //region ALL FUNCTIONS

    fun updateItems(items: List<String>?) {
        hostList = items ?: emptyList()
        filterList = items ?: emptyList()
        notifyDataSetChanged()
    }
    //endregion ALL FUNCTIONS
}