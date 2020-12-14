package com.strider.taskmanager.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strider.taskmanager.database.AppDatabase
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status
import com.strider.taskmanager.preferenses.ApplicationPrefs
import com.strider.taskmanager.events.TasksEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

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
            database.taskDao.insert(Task("Buy groceries", status = Status.DECLINED.id))
            database.taskDao.insert(Task("Prepare food", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Call mom", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Visit grandma", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Repair my bike", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Call Elon Musk", priority = Priority.LOW.id))
            database.taskDao.insert(Task("Wash thgdsae dishes", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Do afsasfthe laundry", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Buy grocefaries", status = Status.DECLINED.id))
            database.taskDao.insert(Task("Prepare asffood", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Calasffal mom", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Visit grasffasandma", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Repair my biafssafke", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Callasffas Elon Musk", priority = Priority.LOW.id))
            database.taskDao.insert(Task("Wash the dasishes", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Do the laundsaasry", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Buy asfafsgroceries", status = Status.DECLINED.id))
            database.taskDao.insert(Task("Prepare foasfod", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Cassasafall mom", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Visit grasfsfaandma", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Repair my bifasafske", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Call Elon Musfasasfk", priority = Priority.LOW.id))
            database.taskDao.insert(Task("Wdash the dishes", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Dod the laundry", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Buyd groceries", status = Status.DECLINED.id))
            database.taskDao.insert(Task("Prepdare food", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Call dmom", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Visit dgrandma", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Repair dmy bike", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Call Elodn Musk", priority = Priority.LOW.id))
            database.taskDao.insert(Task("Wash thgddsae dishes", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Do afsasftdhe laundry", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Buy grocefadries", status = Status.DECLINED.id))
            database.taskDao.insert(Task("Preparde asffood", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Cala3sffdal mom", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Visit3 grdasffa3sandma", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Repair3 myd bi3afssafke", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Callasf3fasd 3Elon Musk", priority = Priority.LOW.id))
            database.taskDao.insert(Task("Wash the3 da3ddsishes", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Do the la3u3ndsdaasry", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Buy asfaf33sgrocderies", status = Status.DECLINED.id))
            database.taskDao.insert(Task("Prepare 3foasfodd", priority = Priority.MEDIUM.id))
            database.taskDao.insert(Task("Cassasa3fall mom", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Visit 3grasfsfaandma", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Repai3r my bifasafske", priority = Priority.HIGH.id))
            database.taskDao.insert(Task("Call3 Elon Musfasasfk", priority = Priority.LOW.id))
        }
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        database.taskDao.update(task.copy(isCompleted = isChecked))
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
        val count = database.taskDao.deleteTask(list)
        Timber.e("qwe count $count")
    }

    fun deleteAllCompletedTasks() = viewModelScope.launch {
        val taskList = database.taskDao.getAll()
            .filter { it.isCompleted }
            .mapTo(mutableListOf(), { it.id })
        val count = database.taskDao.deleteTask(taskList)
        Timber.e("qwe count $count")
    }
}