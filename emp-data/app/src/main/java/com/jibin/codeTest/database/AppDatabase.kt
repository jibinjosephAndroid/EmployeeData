package com.jibin.codeTest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jibin.codeTest.database.dao.EmployeeDao
import com.jibin.codeTest.database.model.Employee
import com.jibin.codeTest.database.typeconverter.AddressTypeConverter
import com.jibin.codeTest.database.typeconverter.CompanyTypeConverter


@Database(
    entities = [Employee::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    AddressTypeConverter::class, CompanyTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                synchronized(this) {
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "EmployeeData.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return appDatabase!!
        }
    }
}