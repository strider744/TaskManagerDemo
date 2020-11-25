package com.strider.taskmanager.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task_table")
@Parcelize
data class DBTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val description: String,
    val creatorName: String,
    val status: Status,
    val priority: Priority,
    val changes: List<Change> = listOf(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastChange: Long
) : Parcelable {

    @Parcelize
    data class Change(
        val changer: String,
        val time: Long,
        val change: String
    ): Parcelable
}