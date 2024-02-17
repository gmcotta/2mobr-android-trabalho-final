package com.gmcotta.a2mbor_trabalho_final.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmcotta.a2mbor_trabalho_final.databinding.EventListItemBinding
import com.gmcotta.a2mbor_trabalho_final.model.Event
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate

class HomeAdapter:ListAdapter<Event, HomeAdapter.ViewHolder>(Event.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class ViewHolder(private val binding: EventListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event?) = with(binding) {
            event?.let {
                tvName.text = event.name
                tvDate.text = event.date?.let { date -> DateFormat.getDateInstance().format(date) }
                tvTime.text = event.time?.let { time -> DateFormat.getTimeInstance(DateFormat.SHORT).format(time) }
                tvAddress.text = event.address
            }
        }
    }
}