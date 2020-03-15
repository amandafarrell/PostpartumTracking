package com.amandafarrell.www.postpartumtracking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.amandafarrell.www.postpartumtracking.database.EventDatabase
import com.amandafarrell.www.postpartumtracking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val application = requireNotNull(this.application)
        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao
        val viewModelFactory = EventTrackerViewModelFactory(dataSource, application)
        val eventTrackerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EventTrackerViewModel::class.java)

        binding.eventTrackerViewModel = eventTrackerViewModel
        binding.lifecycleOwner = this
    }
}
