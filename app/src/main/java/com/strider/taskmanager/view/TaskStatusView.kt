package com.strider.taskmanager.view

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
        Timber.e("qwe status $statusId")

        when (statusId) {
            Status.DECLINED.id -> setViewData(R.drawable.bg_status_declined, Status.DECLINED.status)
            Status.REVIEW.id -> setViewData(R.drawable.bg_status_review, Status.REVIEW.status)
            Status.WIP.id -> setViewData(R.drawable.bg_status_wip, Status.WIP.status)
            Status.TODO.id -> setViewData(R.drawable.bg_status_todo, Status.TODO.status)
            else -> setViewData(R.drawable.bg_status_none, Status.NONE.status)
        }
    }

    // устанавливаем статус
    fun setStatus(status: String) {
        Timber.e("qwe status $status")
        when (status) {
            Status.DECLINED.status -> setViewData(R.drawable.bg_status_declined, Status.DECLINED.status)
            Status.REVIEW.status -> setViewData(R.drawable.bg_status_review, Status.REVIEW.status)
            Status.WIP.status -> setViewData(R.drawable.bg_status_wip, Status.WIP.status)
            Status.TODO.status -> setViewData(R.drawable.bg_status_todo, Status.TODO.status)
            else -> setViewData(R.drawable.bg_status_none, Status.NONE.status)
        }
    }

    // устанавливаем статус
    fun setStatus(status: Status) {
        when (status) {
            Status.DECLINED -> setViewData(R.drawable.bg_status_declined, Status.DECLINED.status)
            Status.REVIEW -> setViewData(R.drawable.bg_status_review, Status.REVIEW.status)
            Status.WIP -> setViewData(R.drawable.bg_status_wip, Status.WIP.status)
            Status.TODO  -> setViewData(R.drawable.bg_status_todo, Status.TODO.status)
            else -> setViewData(R.drawable.bg_status_none, Status.NONE.status)
        }
    }

    // устанавливаем аттрибуты
    private fun setViewData(@DrawableRes id: Int, text: String) {
        tv_task_status.text = text
        tv_task_status.setBackgroundResource(id)
    }
}