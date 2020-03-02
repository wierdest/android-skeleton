package me.wierdest.myapplication.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*

class MyViewModel(

    private val sessionDatabase: SessionDAO,
    application: Application

) : AndroidViewModel(application) {

    val lastSession = sessionDatabase.getLastSession()


    /**
     * Transformations
     */
    val lastSessionId  = Transformations.map(lastSession) {
        when (it) {
            null -> { // do nothing
            }
            else -> {
                return@map it.sessionId
            }
        }
    }

    /**
     * Coroutines
     */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Basic add & remove functions
     */

    fun addNewSession() {
        uiScope.launch {
            insertSession(Session())
        }
    }

    private suspend fun insertSession(item: Session) {
        withContext(Dispatchers.IO) {
            sessionDatabase.insert(item)
        }
    }

    fun removeSession(id: Long) {
        uiScope.launch {
            deleteSession(id)
        }
    }
    private suspend fun deleteSession(id: Long) {
        withContext(Dispatchers.IO) {
            sessionDatabase.clearSession(id)
        }
    }

    fun clearAll() {
        uiScope.launch {
            // add other clear functions from other DAOs as needed
            clearAllSessions()

        }
    }
    private suspend fun clearAllSessions() {
        withContext(Dispatchers.IO) {
            sessionDatabase.clear()
        }
    }



}