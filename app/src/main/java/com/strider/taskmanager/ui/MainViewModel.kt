package com.strider.taskmanager.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.preferenses.ApplicationPrefs
import com.strider.taskmanager.events.TasksEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

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

    // для отладки, заполняем бд
    fun setUpDatabase() {
        viewModelScope.launch {
            database.taskDao.deleteAllData()
            val list = mutableListOf<Task>()
            for (i in 0..100) {
                val name = Random.nextLong().toString() + Random.nextLong().toString()
                val priority = Random.nextInt(0, 4)
                val status = Random.nextInt(0, 5)
                list.add(Task(name, priority = priority, status = status))
            }
            database.taskDao.insert(list.toList())
        }
    }

    fun onTaskCheckedChanged(taskId: Int, isChecked: Boolean) = viewModelScope.launch {
        val task = database.taskDao.getTaskById(taskId)
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

    fun confirmDeleteTask() = viewModelScope.launch {
        val list = database.taskDao.getAll()
            .filter { it.isDeleted }
            .mapTo(mutableListOf(), { it.id })
        database.taskDao.deleteTask(list)
    }

    fun deleteAllCompletedTasks() = viewModelScope.launch {
        val taskList = database.taskDao.getAll()
            .filter { it.isCompleted }
            .mapTo(mutableListOf(), { it.id })
        database.taskDao.deleteTask(taskList)
    }
}