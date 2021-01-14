package me.wierdest.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Simple database access object for our Sessions. Some basic queries.
 */

@Dao
interface TabDAO {
    @Insert
    fun insert(item: Tab)
    @Update
    fun update(item: Tab)
    @Query("DELETE FROM tab_table")
    fun clear()
    @Query("DELETE FROM tab_table WHERE tabId = :key")
    fun clearTab(key: Long)
    @Query("SELECT * FROM tab_table WHERE tabId = :key")
    fun getById(key: Long) : Tab?
    @Query("SELECT * FROM tab_table WHERE creationTimeMilli = :key")
    fun getByCreationTimeMilli(key: Long) : Tab?
    @Query("SELECT * FROM tab_table ORDER BY tabId DESC LIMIT 1")
    fun getLastTabLive() : LiveData<Tab>
    @Query("SELECT * FROM tab_table ORDER BY tabId DESC LIMIT 1")
    fun getLastTab() : Tab?
    @Query("SELECT * from tab_table ORDER BY tabId")
    fun getAllTabsLive() : LiveData<List<Tab>>
    @Query("SELECT * from tab_table WHERE raw = :key LIMIT 1")
    fun getTabByRaw(key: String) : Tab?
}