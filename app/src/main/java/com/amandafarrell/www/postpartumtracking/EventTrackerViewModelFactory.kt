package com.amandafarrell.www.postpartumtracking

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amandafarrell.www.postpartumtracking.database.EventDatabaseDao

class EventTrackerViewModelFactory(
    private val dataSource: EventDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventTrackerViewModel::class.java)) {
            return EventTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
