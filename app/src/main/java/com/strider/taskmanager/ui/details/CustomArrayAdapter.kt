package com.strider.taskmanager.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.strider.taskmanager.R
import com.strider.taskmanager.enums.Status
import kotlinx.android.synthetic.main.view_task_status.view.*

class CustomArrayAdapter(
    context: Context,
    resource: Int,
    objects: Array<out Status>,
    private var inflater: LayoutInflater
) : ArrayAdapter<Status>(context, resource, objects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomDropDownView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomDropDownView(position, convertView, parent)
    }

    private fun getCustomDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.view_task_status, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_task_status)
        setStatus(position, textView)
        return view
    }

    // устанавливаем статус
    private fun setStatus(statusId: Int, view: TextView) {
        when (statusId) {
            Status.DECLINED.id -> setData(R.drawable.bg_status_declined, Status.DECLINED.status, view)
            Status.REVIEW.id -> setData(R.drawable.bg_status_review, Status.REVIEW.status, view)
            Status.WIP.id -> setData(R.drawable.bg_status_wip, Status.WIP.status, view)
            Status.TODO.id -> setData(R.drawable.bg_status_todo, Status.TODO.status, view)
            Status.NONE.id -> setData(R.drawable.bg_status_none, Status.NONE.status, view)
        }
    }

    // устанавливаем аттрибуты
    private fun setData(@DrawableRes id: Int, text: String, view: TextView) {
        view.text = text
        view.setBackgroundResource(id)
    }


}