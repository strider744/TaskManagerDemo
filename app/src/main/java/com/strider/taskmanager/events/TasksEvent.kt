package com.strider.taskmanager.events

import com.strider.taskmanager.database.entity.Task

sealed class TasksEvent {
    data class ShowUndoDeleteMessage(val task: Task): TasksEvent()
}