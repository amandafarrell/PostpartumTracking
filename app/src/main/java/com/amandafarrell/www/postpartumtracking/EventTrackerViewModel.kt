package com.amandafarrell.www.postpartumtracking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.amandafarrell.www.postpartumtracking.database.EventDatabaseDao

class EventTrackerViewModel (
    val database: EventDatabaseDao,
    application: Application) : AndroidViewModel(application) {

}