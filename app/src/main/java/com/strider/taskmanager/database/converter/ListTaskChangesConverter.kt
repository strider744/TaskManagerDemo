package com.strider.taskmanager.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.strider.taskmanager.database.entity.Task
import java.lang.reflect.Type

class ListTaskChangesConverter {
    val gson = Gson()

    val type: Type = object : TypeToken<List<Task?>?>() {}.type

    @TypeConverter
    fun fromList(list: List<Task?>?): String {
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toList(json: String?): List<Task> {
        return gson.fromJson(json, type)
    }
}