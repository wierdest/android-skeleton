package me.wierdest.myapplication.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import me.wierdest.myapplication.utilities.convertLongToDateString

class MyViewModel(

    private val tabDatabase: TabDAO,
    application: Application

) : AndroidViewModel(application) {

    private val TAG = javaClass.simpleName

    val lastTab = tabDatabase.getLastTabLive()
    val allTabs = tabDatabase.getAllTabsLive()

    /**
     * Coroutines
     */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Basic add & remove functions
     */

    /**
     * Safely adding unique sources:
     */
    private val _isSourceUnique = MutableLiveData<Boolean>()
    val isSourceUnique: LiveData<Boolean> = _isSourceUnique
    fun resetIsSourceUnique() {
        uiScope.launch { _isSourceUnique.value = null }
    }

    fun addNewTab(source: String) {
        uiScope.launch {
            _isSourceUnique.value = withContext(Dispatchers.IO) {
                val testSource = tabDatabase.getTabByRaw(source)
                if (testSource == null) {
                    tabDatabase.insert(Tab(raw = source, name = "Added in ${System.currentTimeMillis().convertLongToDateString()}"))
                    true
                } else {
                    false
                }
            }
        }
    }

    fun addNameToLastTab(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val lastTab = tabDatabase.getLastTab()
                lastTab?.let {
                    it.name = name
                    tabDatabase.update(it)
                }

            }
        }
    }

    fun removeTab(id: Long) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                tabDatabase.clearTab(id)
            }
        }
    }

    fun clearAll() {
        uiScope.launch {
            // add other clear functions from other DAOs as needed
            withContext(Dispatchers.IO) {
                tabDatabase.clear()
            }

        }
    }

    /**
     * GET FUNCTIONS
     */
    private val _tabToRead = MutableLiveData<Tab>()
    val tabToRead: LiveData<Tab> = _tabToRead
    fun pickTabToRead(id: Long) {
        uiScope.launch {
            _tabToRead.value = withContext(Dispatchers.IO) {
                tabDatabase.getById(id)
            }
        }
    }
    fun resetTabToRead() {
        uiScope.launch {
            _tabToRead.value = null
        }
    }




}