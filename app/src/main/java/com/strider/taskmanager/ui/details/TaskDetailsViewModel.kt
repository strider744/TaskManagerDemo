package com.strider.taskmanager.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.strider.taskmanager.database.AppDatabase

class TaskDetailsViewModel @ViewModelInject constructor(
    private val database: AppDatabase
) : ViewModel() {


}