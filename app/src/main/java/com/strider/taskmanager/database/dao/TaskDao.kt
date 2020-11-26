package com.strider.taskmanager.database.dao

import androidx.room.*
import com.strider.taskmanager.database.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<Task>)

    // update
    @Update
    suspend fun update(task: Task)

    // query
    @Query("SELECT * FROM task_table")
    fun getAll(): Flow<List<Task>>

    // delete
    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    // delete
    @Query("DELETE FROM task_table")
    suspend fun deleteAllData(): Int
}