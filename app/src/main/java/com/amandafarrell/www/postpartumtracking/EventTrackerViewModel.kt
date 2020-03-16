package com.amandafarrell.www.postpartumtracking

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.amandafarrell.www.postpartumtracking.database.Event
import com.amandafarrell.www.postpartumtracking.database.EventDatabaseDao
import kotlinx.coroutines.*

class EventTrackerViewModel(
    val database: EventDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var latestEvent = MutableLiveData<Event?>()
    private val events = database.getAllEvents()

    val eventsString = Transformations.map(events) { events ->
        formatEvents(events, application.resources)
    }

    init {
        initializeLatestEvent()
    }

    private fun initializeLatestEvent() {
        uiScope.launch {
            latestEvent.value = getLatestEventFromDatabase()
        }
    }

    private suspend fun getLatestEventFromDatabase(): Event? {
        return withContext(Dispatchers.IO) {
            var event = database.getLatest()
            if (event?.endTimeMilli != event?.startTimeMilli) {
                event = null
            }
            event
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newEvent = Event()
            insert(newEvent)
            latestEvent.value = getLatestEventFromDatabase()
        }
    }

    private suspend fun insert(event: Event) {
        withContext(Dispatchers.IO) {
            database.insert(event)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldEvent = latestEvent.value ?: return@launch
            oldEvent.endTimeMilli = System.currentTimeMillis()
            update(oldEvent)
        }
    }

    private suspend fun update(event: Event) {
        withContext(Dispatchers.IO) {
            database.update(event)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            latestEvent.value = null
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}