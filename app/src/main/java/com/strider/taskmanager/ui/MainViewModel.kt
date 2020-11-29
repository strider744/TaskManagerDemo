package com.strider.taskmanager.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status
import com.strider.taskmanager.preferenses.ApplicationPrefs
import com.strider.taskmanager.ui.events.TasksEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {

    private val eventChannel = Channel<TasksEvent>()
    val events = eventChannel.receiveAsFlow()

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

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        database.taskDao.update(task.copy(isCompleted = isChecked))
    }

    fun onTaskSwiped(task: Task) {
        deleteTask(task)
    }

    private fun deleteTask(task: Task) = viewModelScope.launch {
        database.taskDao.update(task.copy(isDeleted = true))
        eventChannel.send(TasksEvent.ShowUndoDeleteMessage(task))
    }

    fun onUndoDeleteClick(task: Task) = viewModelScope.launch {
        database.taskDao.update(task.copy(isDeleted = false))
    }
}