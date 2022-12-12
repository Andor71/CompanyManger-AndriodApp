package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
data class LoginResponse(
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("token")
    var token: String,
    @SerializedName("deadline")
    var deadline: Long,
) {
    override fun toString(): String {
        return "LoginResponse(" +
                "userId='$userId'," +
                " token='$token'," +
                " deadline=$deadline)"
    }
}