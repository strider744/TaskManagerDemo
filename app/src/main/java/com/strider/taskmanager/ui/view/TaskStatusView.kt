package com.strider.taskmanager.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.strider.taskmanager.R
import com.strider.taskmanager.enums.Status
import kotlinx.android.synthetic.main.view_task_status.view.*
import timber.log.Timber

class TaskStatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_task_status, this)
        setAttrs(attrs)
    }

    // устанавливаем аттрибуты
    private fun setAttrs(attrs: AttributeSet?) {
        kotlin.runCatching {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TaskStatusView)

            setStatus(typedArray.getInt(R.styleable.TaskStatusView_status, Status.NONE.id))

            typedArray.recycle()
        }.onFailure {
            Timber.e(it.localizedMessage)
        }
    }

    // устанавливаем статус
    fun setStatus(statusId: Int) {
        when (statusId) {
            Status.DECLINED.id -> setData(R.drawable.bg_status_declined, Status.DECLINED.status)
            Status.REVIEW.id -> setData(R.drawable.bg_status_review, Status.REVIEW.status)
            Status.WIP.id -> setData(R.drawable.bg_status_wip, Status.WIP.status)
            Status.TODO.id -> setData(R.drawable.bg_status_todo, Status.TODO.status)
            else -> setData(R.drawable.bg_status_none, Status.NONE.status)
        }
    }

    // устанавливаем статус
    fun setStatus(status: Status) {
        setStatus(status.id)
    }

    // устанавливаем аттрибуты
    private fun setData(@DrawableRes id: Int, text: String) {
        tv_task_status.text = text
        tv_task_status.setBackgroundResource(id)
    }
}