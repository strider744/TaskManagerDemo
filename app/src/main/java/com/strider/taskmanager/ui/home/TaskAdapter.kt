package com.strider.taskmanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.databinding.ItemTaskBinding
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status

class TaskAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }

                cbCompleted.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onCheckBoxClick(task, cbCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                cbCompleted.isChecked = task.isCompleted
                tvTaskTitle.text = task.name

                if (task.status == Status.NONE.id) {
                    taskStatusView.visibility = View.GONE
                } else {
                    taskStatusView.visibility = View.VISIBLE
                    taskStatusView.setStatus(task.status)
                }
                if (task.priority == Priority.NONE.id) {
                    taskPriorityView.visibility = View.GONE
                } else {
                    taskPriorityView.visibility = View.VISIBLE
                    taskPriorityView.setPriority(task.priority)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}