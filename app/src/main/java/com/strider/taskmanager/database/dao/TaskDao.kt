package com.strider.taskmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.enums.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Task>)

    // update
    @Update
    suspend fun update(task: Task)

    @Update
    suspend fun update(list: List<Task>)

    // query
    @Query("SELECT * FROM task_table")
    suspend fun getAll(): List<Task>

    @Query("SELECT * FROM task_table WHERE id = :id")
    suspend fun getTaskById(id: Int): Task

    // query
    fun getTasks(
        query: String,
        sortOrder: SortOrder = SortOrder.BY_DATE,
        hideCompleted: Boolean = false
    ): Flow<List<Task>> {
        return when(sortOrder) {
//            SortOrder.BY_CREATOR -> getSortedByCreator(query, hideCompleted)
            SortOrder.BY_DATE -> getSortedByDate(query, hideCompleted)
            SortOrder.BY_NAME -> getSortedByName(query, hideCompleted)
            SortOrder.BY_PRIORITY -> getSortedByPriority(query, hideCompleted)
        }
    }

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND (isDeleted = 0) AND name LIKE '%' || :query || '%' ORDER BY name")
    fun getSortedByName(query: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND (isDeleted = 0) AND name LIKE '%' || :query || '%' ORDER BY lastChange DESC")
    fun getSortedByDate(query: String, hideCompleted: Boolean): Flow<List<Task>>

//    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND (isDeleted = 0) AND name LIKE '%' || :query || '%' ORDER BY creatorName")
//    fun getSortedByCreator(query: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted OR isCompleted = 0) AND (isDeleted = 0) AND name LIKE '%' || :query || '%' ORDER BY priority DESC")
    fun getSortedByPriority(query: String, hideCompleted: Boolean): Flow<List<Task>>

    // delete
    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTask(id: Int): Int

    @Query("DELETE FROM task_table WHERE id IN (:list)")
    suspend fun deleteTask(list: List<Int>): Int

    // delete
    @Query("DELETE FROM task_table")
    suspend fun deleteAllData(): Int
}