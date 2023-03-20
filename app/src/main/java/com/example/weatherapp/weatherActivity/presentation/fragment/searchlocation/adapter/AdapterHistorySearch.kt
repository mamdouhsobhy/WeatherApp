package com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.core.presentation.extensions.layoutInflater
import com.example.weatherapp.databinding.LayoutItemSearchHistoryBinding
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory

class AdapterHistorySearch(private val itemSelected: (String) -> Unit) :
    ListAdapter<ModelSearchHistory, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutItemSearchHistoryBinding.inflate(parent.layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)

        }
    }

    private inner class ViewHolder(private val binding: LayoutItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item: ModelSearchHistory) = with(binding) {

            binding.tvSearchHistory.text = item.searchName
            binding.itemSearch.setOnClickListener {
                itemSelected(item.searchName)
                for (i in 0 until currentList.size) {
                    currentList[i].selected = false
                }
                item.selected = true
                notifyDataSetChanged()
            }

            binding.selectSearchHistory.isChecked = item.selected

        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelSearchHistory>() {
            override fun areItemsTheSame(
                oldItem: ModelSearchHistory,
                newItem: ModelSearchHistory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ModelSearchHistory,
                newItem: ModelSearchHistory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}