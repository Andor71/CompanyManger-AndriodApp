package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class UserUpdateDto (
    @SerializedName("ID")
    var ID: Int,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("location")
    var location: String,
    @SerializedName("phoneNumber")
    var phoneNumber: Int,
    @SerializedName("departmentId")
    var departmentId:Int = 0,
    @SerializedName("mentorId")
    var mentorId:Int = 0,
    @SerializedName("image")
    var imagine: Any? = null
)