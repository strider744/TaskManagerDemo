package com.strider.taskmanager.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.SortOrder
import com.strider.taskmanager.enums.Status
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {

    val searchQuery = MutableLiveData("")
    val sortOrder = MutableLiveData(SortOrder.BY_DATE)
    val hideCompleted = MutableLiveData(false)

    val tasks = Transformations.switchMap(searchQuery) {
        database.taskDao.getTasks(query = it)
    }

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