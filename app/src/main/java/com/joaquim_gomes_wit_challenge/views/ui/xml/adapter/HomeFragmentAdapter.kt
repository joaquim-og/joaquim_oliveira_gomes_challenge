package com.joaquim_gomes_wit_challenge.views.ui.xml.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.extensions.mapWeatherIcon
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.databinding.CardHomeRecyclerViewBinding
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel

class HomeFragmentAdapter(
    private val events: List<ScreenWeatherInfo>,
    private val homeViewModel: HomeViewModel,
    val onClick: (ScreenWeatherInfo) -> Unit
) : RecyclerView.Adapter<HomeFragmentAdapter.EventsView>() {

    private var onAttach: Boolean = false
    private val duration: Long = 200

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsView = EventsView(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_home_recycler_view,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsView, position: Int) {
        val eventDetailsItem = events[position]
        holder.binding.wheaterItem = eventDetailsItem
        holder.binding.root.setOnClickListener { onClick.invoke(eventDetailsItem) }
        holder.bind(eventDetailsItem, position)
        setAnimation(holder.itemView)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onAttach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        super.onAttachedToRecyclerView(recyclerView)
    }

    private fun setAnimation(itemView: View) {
        itemView.alpha = 0f

        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animator.startDelay = duration / 2
        animator.duration = 250
        animatorSet.play(animator)
        animator.start()

    }

    inner class EventsView(val binding: CardHomeRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherData: ScreenWeatherInfo, position: Int) {
            with(binding) {
                cardHomeEventItemTitle.text = weatherData.nameCity
                cardHomeEventItemTemp.text = cardHomeEventItemTemp.context.getString(
                    R.string.general_text_place_holder_description_weather_temp
                ).replace("#weather_temp", weatherData.temperatureActual ?: "")
                cardHomeEventItemDate.text = weatherData.date
                cardHomeEventItemWeatherDescription.text = weatherData.descriptionWeather
                cardHomeImgAnimation.setAnimation(weatherData.icon?.mapWeatherIcon())
            }
        }
    }

}

@BindingAdapter("getEventWeatherDescription")
fun setEventPrice(textView: TextView, eventWeatherDescription: String) {
    textView.text = textView.context.getString(
        R.string.general_text_place_holder_description_weather_text
    ).replace("#weather_description", eventWeatherDescription)
}