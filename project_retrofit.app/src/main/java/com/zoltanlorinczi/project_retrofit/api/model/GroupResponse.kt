package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("ID")
    var id:Int,
    @SerializedName("name")
    var name:String,
)
