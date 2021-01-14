package me.wierdest.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_table")
data class Tab(
    @PrimaryKey(autoGenerate = true)
    var tabId: Long = 0L,
    @ColumnInfo(name = "raw")
    var raw: String,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "creationTimeMilli")
    val creationTimeMilli: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "lastAccessTimeMilli")
    val lastAccessTimeMilli: Long = 0L

)