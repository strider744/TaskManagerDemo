package com.strider.taskmanager.database.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task_table")
@Parcelize
data class Task(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val isCompleted: Boolean = false,
    val description: String = "",
    val creatorName: String = "",
    val status: String = Status.WIP.status,
    val priority: Int = Priority.NONE.id,
    val changes: List<Change> = listOf(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastChange: Long = System.currentTimeMillis()
) : Parcelable {

    @Parcelize
    data class Change(
        val changer: String = "",
        val time: Long = System.currentTimeMillis(),
        val change: String = ""
    ): Parcelable
}