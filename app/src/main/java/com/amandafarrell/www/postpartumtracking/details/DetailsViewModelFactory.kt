package com.amandafarrell.www.postpartumtracking.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amandafarrell.www.postpartumtracking.EventTrackerViewModel
import com.amandafarrell.www.postpartumtracking.database.EventDatabaseDao

class DetailsViewModelFactory(
    private val eventKey: Long,
    private val dataSource: EventDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(eventKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}