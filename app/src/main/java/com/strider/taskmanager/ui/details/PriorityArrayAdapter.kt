package com.strider.taskmanager.ui.details

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.strider.taskmanager.R
import com.strider.taskmanager.enums.Priority
import org.jetbrains.anko.textColor

class PriorityArrayAdapter(
    context: Context,
    resource: Int,
    objects: Array<out Priority>,
    private var inflater: LayoutInflater
) : ArrayAdapter<Priority>(context, resource, objects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomDropDownView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomDropDownView(position, convertView, parent)
    }

    private fun getCustomDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.view_task_priority, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_task_priority)
        setPriority(position, textView)
        return view
    }

    // устанавливаем приоритет
    private fun setPriority(priorityId: Int, view: TextView) {
        when (priorityId) {
            Priority.LOW.id -> setData(R.drawable.bg_priority_low, Priority.LOW.priority, view)
            Priority.MEDIUM.id -> setData(R.drawable.bg_priority_medium, Priority.MEDIUM.priority, view)
            Priority.HIGH.id -> setData(R.drawable.bg_priority_high, Priority.HIGH.priority, view)
            else -> setData(R.drawable.bg_status_none, Priority.NONE.priority, view)
        }
    }

    // устанавливаем аттрибуты
    private fun setData(@DrawableRes id: Int, text: String, view: TextView) {
        if (text == Priority.NONE.priority)
            view.setTextColor(Color.WHITE)
        else
            view.setTextColor(Color.BLACK)
        view.text = text
        view.setBackgroundResource(id)
    }


}