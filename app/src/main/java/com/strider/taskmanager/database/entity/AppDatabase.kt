package com.strider.taskmanager.database.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.strider.taskmanager.database.dao.TaskDao

@Database(
    entities = [DBTask::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
}