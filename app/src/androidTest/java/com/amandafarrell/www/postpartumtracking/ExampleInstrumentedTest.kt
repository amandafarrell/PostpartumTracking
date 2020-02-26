package com.amandafarrell.www.postpartumtracking

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amandafarrell.www.postpartumtracking.database.Event
import com.amandafarrell.www.postpartumtracking.database.EventDatabase
import com.amandafarrell.www.postpartumtracking.database.EventDatabaseDao
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.amandafarrell.www.postpartumtracking", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class EventDatabaseTest {

    private lateinit var eventDao: EventDatabaseDao
    private lateinit var db: EventDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, EventDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        eventDao = db.eventDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEvent() {
        val event = Event()
        eventDao.insert(event)
        val latestEvent = eventDao.getLatest()
        assertEquals(latestEvent?.description, "")
    }
}


