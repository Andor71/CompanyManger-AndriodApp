package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class UserUpdateDto (

    @SerializedName("lastName")
    var lastName    : String,
    @SerializedName("firstName")
    var firstName   : String,
    @SerializedName("location")
    var location    : String,
    @SerializedName("phoneNumber")
    var phoneNumber : String,
    @SerializedName("imageUrl")
    var imageUrl    : String
)