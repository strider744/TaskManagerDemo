package com.strider.taskmanager.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import kotlinx.coroutines.launch

class TaskDetailsViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {

    var currentTask = Task("")

    fun onTaskStatusChanged(status: Int) = viewModelScope.launch {
        currentTask = currentTask.copy(status = status)
    }

    fun onTaskPriorityChanged(priority: Int) = viewModelScope.launch {
        currentTask = currentTask.copy(priority = priority)
    }

    fun saveTask() = viewModelScope.launch {
        if (currentTask.name.isNotBlank()) {
            database.taskDao.insert(currentTask)
        }
    }
}