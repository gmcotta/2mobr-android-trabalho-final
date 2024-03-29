package com.gmcotta.a2mbor_trabalho_final.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Event(
    var id: String = UUID.randomUUID().toString(),
    var name: String? = null,
    var date: Date? = null,
    var time: Date? = null,
    var address: String? = null
): Parcelable {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Event> =
            object : DiffUtil.ItemCallback<Event>() {
                override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}
