package com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.utilities.AppUtil
import com.example.weatherapp.core.presentation.extensions.layoutInflater
import com.example.weatherapp.databinding.LayoutItemForecastDaysBinding
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.FiveDays
import java.text.SimpleDateFormat
import java.util.*

class AdapterForecastDays :
    ListAdapter<FiveDays, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutItemForecastDaysBinding.inflate(parent.layoutInflater, parent, false)

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

    private inner class ViewHolder(private val binding: LayoutItemForecastDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ShowToast", "SetTextI18n", "NotifyDataSetChanged", "SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item: FiveDays) = with(binding) {
            val animationSlideOut = AnimationUtils.loadAnimation(root.context, R.anim.slide_out_left)
            val animationSlideIn = AnimationUtils.loadAnimation(root.context, R.anim.slide_in_right)
            val inFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date: Date = inFormat.parse(item.dt_txt)!!
            val outFormat = SimpleDateFormat("EEEE")
            val clockFormat = SimpleDateFormat("HH:mm a")
            val dayName: String = outFormat.format(date)
            val clock: String = clockFormat.format(date)
            binding.tvDate.text= "$dayName  $clock"

            binding.tvTemprature.startAnimation(animationSlideOut)
            binding.tvTemprature.startAnimation(animationSlideIn)
            binding.tvDescriptionTemp.startAnimation(animationSlideOut)
            binding.tvDescriptionTemp.startAnimation(animationSlideIn)


            binding.tvTemprature.text = java.lang.String.format(Locale.getDefault(), "%.0f°",
                item.main.temp_max)+" "+java.lang.String.format(
                Locale.getDefault(), "%.0f°", item.main.temp_min)
            binding.tvDescriptionTemp.text = item.weather[0].description
            binding.animationView.setAnimation(AppUtil.getWeatherAnimation(item.weather[0].id))
            binding.animationView.playAnimation()


        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<FiveDays>() {
            override fun areItemsTheSame(
                oldItem: FiveDays,
                newItem: FiveDays
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: FiveDays,
                newItem: FiveDays
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}