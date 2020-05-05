package com.amandafarrell.www.postpartumtracking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amandafarrell.www.postpartumtracking.database.Event
import kotlinx.android.synthetic.main.list_item.view.*

class EventAdapter(private val events: Array<Event>) :
    RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.start_text.text = events[position].startTimeMilli.toString()
    }

    override fun getItemCount() = events.size
}