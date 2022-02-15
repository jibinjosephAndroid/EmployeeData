package com.jibin.codeTest.networking


import com.jibin.codeTest.database.model.Employee
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("5d565297300000680030a986")
    fun getEmployeeData(): Call<List<Employee>>
}