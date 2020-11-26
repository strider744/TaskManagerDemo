package com.strider.taskmanager.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.Status
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {
    val tasks = database.taskDao.getAll().asLiveData()

    fun setUpDatabase() {
        viewModelScope.launch {
            database.taskDao.deleteAllData()
            database.taskDao.insert(Task("Wash the dishes"))
            database.taskDao.insert(Task("Do the laundry"))
            database.taskDao.insert(Task("Buy groceries", status = Status.DECLINED.status))
            database.taskDao.insert(Task("Prepare food"))
            database.taskDao.insert(Task("Call mom"))
            database.taskDao.insert(Task("Visit grandma"))
            database.taskDao.insert(Task("Repair my bike"))
            database.taskDao.insert(Task("Call Elon Musk"))
        }
    }
}