package com.jibin.codeTest.database.repository

import android.content.Context
import com.jibin.codeTest.database.AppDatabase
import com.jibin.codeTest.database.model.Employee


class EmployeeRepository(application: Context) {

    //Dao
    private val employeeDao = AppDatabase.getInstance(application).employeeDao()

    suspend fun insert(listOfEmployees: List<Employee>) {
        employeeDao.insert(listOfEmployees)
    }

    suspend fun getEmployeeList() = employeeDao.getEmployees()
}