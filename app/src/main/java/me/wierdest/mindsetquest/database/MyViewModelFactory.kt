package me.wierdest.mindsetquest.database

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyViewModelFactory(

    private val sessionDataSource: SessionDAO,
    private val application: Application

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(
                sessionDataSource,
                application
            ) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }


}
