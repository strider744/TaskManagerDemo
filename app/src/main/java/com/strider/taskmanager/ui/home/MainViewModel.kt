package com.strider.taskmanager.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strider.taskmanager.App
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.SortOrder
import com.strider.taskmanager.enums.Status
import com.strider.taskmanager.preferenses.ApplicationPrefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {

    val searchQuery = MutableLiveData("")

    val tasksFlow =
        combine(
            searchQuery.asFlow(),
            ApplicationPrefs.sortOrderLiveData.asFlow(),
            ApplicationPrefs.hideCompletedLiveData.asFlow()
        ) { searchQuery, sortOrder, hideCompleted ->
            Triple(searchQuery, sortOrder, hideCompleted)
        }.flatMapLatest { (searchQuery, sortOrder, hideCompleted) ->
            database.taskDao.getTasks(searchQuery, sortOrder, hideCompleted)
        }.asLiveData()

    fun setUpDatabase() {
        viewModelScope.launch {
            database.taskDao.deleteAllData()
            database.taskDao.insert(Task("Wash the dishes", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Do the laundry", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Buy groceries", status = Status.DECLINED.status))
            database.taskDao.insert(Task("Prepare food", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Call mom"))
            database.taskDao.insert(Task("Visit grandma"))
            database.taskDao.insert(Task("Repair my bike"))
            database.taskDao.insert(Task("Call Elon Musk", priority = Priority.LOW.id))
        }
    }
}