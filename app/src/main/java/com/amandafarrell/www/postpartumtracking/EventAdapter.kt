package com.amandafarrell.www.postpartumtracking

import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.amandafarrell.www.postpartumtracking.database.Event
import com.amandafarrell.www.postpartumtracking.details.DetailsActivity
import java.util.*

class EventAdapter() :
    RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    var data = listOf<Event>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)),
        View.OnClickListener {
        private var mDescriptionText: TextView? = null
        private var mStartTimeText: TextView? = null
        private var mInflater = inflater
        private lateinit var mEvent: Event

        init {
            mDescriptionText = itemView.findViewById(R.id.description_text)
            mStartTimeText = itemView.findViewById(R.id.start_text)
            itemView.setOnClickListener(this)
        }

        fun bind(event: Event) {
            mDescriptionText?.text = event.description
            mStartTimeText?.text = Date(event.startTimeMilli).toString()
            mEvent = event
        }

        override fun onClick(v: View?) {
            val intent = Intent(mInflater.context, DetailsActivity::class.java).apply {
                putExtra(
                    Resources.getSystem().getString(R.string.extra_event), mEvent.eventId.toString()
                )
            }
            mInflater.context.startActivity(intent)
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