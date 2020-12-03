package com.strider.taskmanager.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import kotlinx.coroutines.launch
import timber.log.Timber

class TaskDetailsViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {

    var currentTask = Task("")

    fun onTaskStatusChanged(status: Int) = viewModelScope.launch {
        database.taskDao.update(currentTask.copy(status = status))
    }

    fun saveTask() = viewModelScope.launch {
        database.taskDao.insert(currentTask)
    }
}