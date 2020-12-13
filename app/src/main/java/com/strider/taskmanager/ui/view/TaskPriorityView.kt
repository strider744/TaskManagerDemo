package com.strider.taskmanager.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.strider.taskmanager.R
import com.strider.taskmanager.enums.Priority
import kotlinx.android.synthetic.main.view_task_priority.view.*
import timber.log.Timber

class TaskPriorityView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_task_priority, this)
        setAttrs(attrs)
    }

    // устанавливаем аттрибуты
    private fun setAttrs(attrs: AttributeSet?) {
        kotlin.runCatching {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TaskPriorityView)

            setPriority(typedArray.getInt(R.styleable.TaskPriorityView_priority, Priority.NONE.id))

            typedArray.recycle()
        }.onFailure {
            Timber.e(it.localizedMessage)
        }
    }

    // устанавливаем приоритет
    fun setPriority(priorityId: Int) {
        when (priorityId) {
            Priority.LOW.id -> setData(R.drawable.bg_priority_low, Priority.LOW.priority)
            Priority.MEDIUM.id -> setData(R.drawable.bg_priority_medium, Priority.MEDIUM.priority)
            Priority.HIGH.id -> setData(R.drawable.bg_priority_high, Priority.HIGH.priority)
            else -> setData(R.drawable.bg_status_none, Priority.NONE.priority)
        }
    }

    // устанавливаем статус
    fun setPriority(priority: Priority) {
        setPriority(priority.id)
    }

    // устанавливаем аттрибуты
    private fun setData(@DrawableRes id: Int, text: String) {
        tv_task_priority.text = text
        tv_task_priority.setBackgroundResource(id)
    }

}