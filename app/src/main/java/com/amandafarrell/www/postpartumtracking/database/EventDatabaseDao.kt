package com.amandafarrell.www.postpartumtracking.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDatabaseDao {

    @Insert
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Query("SELECT * from event_table WHERE eventId = :key")
    fun get(key: Long): Event?

    @Query("SELECT * from event_table WHERE eventId = :key")
    fun getLiveEvent(key: Long): LiveData<Event?>

    @Query("DELETE FROM event_table")
    fun clear()

    @Query("SELECT * FROM event_table ORDER BY eventId DESC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event_table ORDER BY eventId DESC LIMIT 1")
    fun getLatest(): Event?

}

