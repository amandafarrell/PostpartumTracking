package com.amandafarrell.www.postpartumtracking

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amandafarrell.www.postpartumtracking.database.Event
import java.util.*

class EventAdapter() :
    RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    var data = listOf<Event>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
        private var mDescriptionText: TextView? = null
        private var mStartTimeText: TextView? = null

        init {
            mDescriptionText = itemView.findViewById(R.id.description_text)
            mStartTimeText = itemView.findViewById(R.id.start_text)
        }

        fun bind(event: Event) {
            mDescriptionText?.text = event.description
            mStartTimeText?.text = Date(event.startTimeMilli).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
        return MyViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event: Event = data[position]
        holder.bind(event)
    }

    override fun getItemCount() = data.size
}