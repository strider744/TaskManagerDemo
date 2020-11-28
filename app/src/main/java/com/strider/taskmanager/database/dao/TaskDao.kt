package com.strider.taskmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.SortOrder

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
    fun getAll(): LiveData<List<Task>>

    // query
    fun getTasks(
        query: String,
        sortOrder: SortOrder = SortOrder.BY_DATE,
        hideCompleted: Boolean = false
    ): LiveData<List<Task>> {
        return when(sortOrder) {
            SortOrder.BY_CREATOR -> getSortedByCreator(query, hideCompleted)
            SortOrder.BY_DATE -> getSortedByDate(query, hideCompleted)
            SortOrder.BY_NAME -> getSortedByName(query, hideCompleted)
            SortOrder.BY_PRIORITY -> getSortedByPriority(query, hideCompleted)
        }
    }

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND name LIKE '%' || :query || '%' ORDER BY name")
    fun getSortedByName(query: String, hideCompleted: Boolean): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND name LIKE '%' || :query || '%' ORDER BY createdAt")
    fun getSortedByDate(query: String, hideCompleted: Boolean): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND name LIKE '%' || :query || '%' ORDER BY creatorName")
    fun getSortedByCreator(query: String, hideCompleted: Boolean): LiveData<List<Task>>


    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND name LIKE '%' || :query || '%' ORDER BY priority DESC")
    fun getSortedByPriority(query: String, hideCompleted: Boolean): LiveData<List<Task>>

    // delete
    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    // delete
    @Query("DELETE FROM task_table")
    suspend fun deleteAllData(): Int
}