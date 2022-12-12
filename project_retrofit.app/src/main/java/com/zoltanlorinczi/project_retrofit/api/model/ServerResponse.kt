package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class ServerResponse (
    @SerializedName("message")
    var message: String,
)