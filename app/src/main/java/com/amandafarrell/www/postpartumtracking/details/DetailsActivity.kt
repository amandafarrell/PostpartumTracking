package com.amandafarrell.www.postpartumtracking.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.amandafarrell.www.postpartumtracking.MainActivity
import com.amandafarrell.www.postpartumtracking.R
import com.amandafarrell.www.postpartumtracking.database.EventDatabase
import com.amandafarrell.www.postpartumtracking.databinding.ActivityDetailsBinding
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_details)

        val application = requireNotNull(this).application
        val arguments = intent.getStringExtra(getString(R.string.extra_event))!!
        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao
        val viewModelFactory = DetailsViewModelFactory(arguments.toLong(), dataSource)
        val detailsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)

        binding.detailsViewModel = detailsViewModel
        binding.lifecycleOwner = this

        text.text = arguments

        detailsViewModel.navigateToEventTracker.observe(this, Observer {
            if(it == true){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                detailsViewModel.doneNavigating()
            }
        })

        button.setOnClickListener(View.OnClickListener {
            detailsViewModel.onSetEventDescription(descriptionEditText.text.toString())
        })
    }
}