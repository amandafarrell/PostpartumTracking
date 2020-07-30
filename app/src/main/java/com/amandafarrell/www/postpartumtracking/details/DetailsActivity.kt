package com.amandafarrell.www.postpartumtracking.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amandafarrell.www.postpartumtracking.MainActivity
import com.amandafarrell.www.postpartumtracking.R
import com.amandafarrell.www.postpartumtracking.database.EventDatabase
import com.amandafarrell.www.postpartumtracking.databinding.ActivityDetailsBinding
import com.amandafarrell.www.postpartumtracking.getFormattedDateString
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_details)

        val application = requireNotNull(this).application
        Log.e("Details, intent", intent.extras?.get(getString(R.string.extra_event)).toString())
        val arguments = intent.extras?.get(getString(R.string.extra_event)).toString()
        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao
        val viewModelFactory = DetailsViewModelFactory(arguments.toLong(), dataSource)
        val detailsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)

        binding.detailsViewModel = detailsViewModel
        binding.lifecycleOwner = this

        detailsViewModel.getEvent().observe(this, Observer {
            it?.let {
                descriptionEditText.setText(it.description)
                editTextDateStart.setText(getFormattedDateString(it.startTimeMilli, "MM/dd"))
                editTextTimeStart.setText(getFormattedDateString(it.startTimeMilli,"hh:mm"))
                editTextDateEnd.setText(getFormattedDateString(it.endTimeMilli, "MM/dd"))
                editTextTimeEnd.setText(getFormattedDateString(it.endTimeMilli,"hh:mm"))
            }
        })

        detailsViewModel.navigateToEventTracker.observe(this, Observer {
            if (it == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                detailsViewModel.doneNavigating()
            }
        })

        button.setOnClickListener({
            detailsViewModel.onSetEventDescription(descriptionEditText.text.toString())
        })
    }
}