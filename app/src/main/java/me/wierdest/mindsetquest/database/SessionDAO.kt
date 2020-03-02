package me.wierdest.mindsetquest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Simple database access object for our Sessions. Some basic queries.
 */

@Dao
interface SessionDAO {
    @Insert
    fun insert(item: Session)
    @Update
    fun update(item: Session)
    @Query("DELETE FROM session_table")
    fun clear()
    @Query("DELETE FROM session_table WHERE sessionId = :key")
    fun clearSession(key: Long)
    @Query("SELECT * FROM session_table WHERE sessionId = :key")
    fun getById(key: Long) : Session?
    @Query("SELECT * FROM session_table WHERE creationTimeMilli = :key")
    fun getByCreationTimeMilli(key: Long) : Session?
    @Query("SELECT * FROM session_table ORDER BY sessionId DESC LIMIT 1")
    fun getLastSession() : LiveData<Session>
}