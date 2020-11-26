package com.strider.taskmanager.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.strider.taskmanager.database.entity.Task
import java.lang.reflect.Type

class ListTaskChangesConverter {
    val gson = Gson()

    val type: Type = object : TypeToken<List<Task.Change?>?>() {}.type

    @TypeConverter
    fun fromList(list: List<Task.Change?>?): String {
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toList(json: String?): List<Task.Change> {
        return gson.fromJson(json, type)
    }
}