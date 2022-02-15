package com.jibin.codeTest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jibin.codeTest.database.model.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: List<Employee>)

    @Query("SELECT * FROM employees")
    suspend fun getEmployees(): List<Employee>

}