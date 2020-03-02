package me.wierdest.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true)
    var sessionId: Long = 0L,
    @ColumnInfo(name = "creationTimeMilli")
    val creationTimeMilli: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "lastAccessTimeMilli")
    val lastAccessTimeMilli: Long = 0L

)