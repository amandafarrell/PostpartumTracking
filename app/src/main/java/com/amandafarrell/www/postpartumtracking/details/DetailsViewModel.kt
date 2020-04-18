package com.amandafarrell.www.postpartumtracking.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amandafarrell.www.postpartumtracking.database.Event
import com.amandafarrell.www.postpartumtracking.database.EventDatabase
import com.amandafarrell.www.postpartumtracking.database.EventDatabaseDao
import kotlinx.coroutines.*

class DetailsViewModel(
    private val eventKey: Long = 0L,
    val database: EventDatabaseDao
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToEventTracker = MutableLiveData<Boolean?>()

    val navigateToEventTracker: LiveData<Boolean?>
        get() = _navigateToEventTracker

    fun doneNavigating(){
        _navigateToEventTracker.value = null
    }

    fun onSetEventDescription(enteredDescription: String){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val currentEvent = database.get(eventKey) ?: return@withContext
                currentEvent.description = enteredDescription
                database.update(currentEvent)
            }
            _navigateToEventTracker.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}