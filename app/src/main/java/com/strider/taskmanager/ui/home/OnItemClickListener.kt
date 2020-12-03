package com.strider.taskmanager.ui.home

import com.strider.taskmanager.database.entity.Task

interface OnItemClickListener {
    fun onItemClick(item: Task)
    fun onCheckBoxClick(item: Task, isChecked: Boolean)
}