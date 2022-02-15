package com.jibin.codeTest.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jibin.codeTest.database.typeconverter.AddressTypeConverter
import com.jibin.codeTest.database.typeconverter.CompanyTypeConverter
import com.jibin.codeTest.database.typeconverter.GeoTypeConverter
import com.jibin.codeTest.utils.emptyString

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "employees")
data class Employee(
    @Json(name = "id")
    @PrimaryKey
    val id: String,

    @Json(name = "name")
    var name: String? = emptyString(),

    @Json(name = "username")
    var username: String? = emptyString(),

    @Json(name = "email")
    var email: String? = emptyString(),

    @Json(name = "profile_image")
    var profile_image: String? = emptyString(),

    @Json(name = "address")
    @TypeConverters(AddressTypeConverter::class)
    var address: Address? = Address(),

    @Json(name = "phone")
    var phone: String? = emptyString(),

    @Json(name = "website")
    var website: String? = emptyString(),

    @Json(name = "company")
    @TypeConverters(CompanyTypeConverter::class)
    var company: Company? = Company()
):Serializable

@JsonClass(generateAdapter = true)
data class Address(
    var street: String? = emptyString(),

    var suite: String? = emptyString(),

    var city: String? = emptyString(),

    var zipcode: String? = emptyString(),

    @TypeConverters(GeoTypeConverter::class)
    var geo: Geo = Geo()
):Serializable

@JsonClass(generateAdapter = true)
data class Geo(var lat: String ? = emptyString(), var lng: String ? = emptyString()):Serializable

@JsonClass(generateAdapter = true)
data class Company(var name: String? = emptyString(), var catchPhrase: String? = emptyString(), var bs: String? = emptyString()):Serializable