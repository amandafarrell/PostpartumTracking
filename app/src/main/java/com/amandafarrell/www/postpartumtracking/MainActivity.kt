package com.amandafarrell.www.postpartumtracking

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amandafarrell.www.postpartumtracking.database.EventDatabase
import com.amandafarrell.www.postpartumtracking.databinding.ActivityMainBinding
import com.amandafarrell.www.postpartumtracking.details.DetailsActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val application = requireNotNull(this.application)
        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao
        val viewModelFactory = EventTrackerViewModelFactory(dataSource, application)
        val eventTrackerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EventTrackerViewModel::class.java)

        binding.eventTrackerViewModel = eventTrackerViewModel
        binding.lifecycleOwner = this

        val adapter = EventAdapter()

        val mDividerItemDecoration = DividerItemDecoration(
            applicationContext,
            DividerItemDecoration.VERTICAL
        )

        //mDividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.horizontal_line))

        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
            addItemDecoration(mDividerItemDecoration)
        }

        eventTrackerViewModel.getEvents().observe(this, Observer {
            it?.let {
                adapter.data = it
            }
        })

        eventTrackerViewModel.navigateToDetailsActivity.observe(this, Observer { event ->
            event?.let {
                val intent = Intent(this, DetailsActivity::class.java).apply {
                    putExtra(getString(R.string.extra_event), event.eventId)
                }
                this.startActivity(intent)
                eventTrackerViewModel.doneNavigating()
            }
        })
    }
}
