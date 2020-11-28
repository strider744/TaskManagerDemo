package com.strider.taskmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.strider.taskmanager.database.converter.ListTaskChangesConverter
import com.strider.taskmanager.database.dao.TaskDao
import com.strider.taskmanager.database.entity.Task

@Database(entities = [Task::class], version = 3, exportSchema = true)
@TypeConverters(ListTaskChangesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}