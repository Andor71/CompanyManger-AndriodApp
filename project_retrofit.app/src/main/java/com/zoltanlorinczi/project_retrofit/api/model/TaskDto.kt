package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class TaskDto (
    @SerializedName("ID")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("assigneeToUserId")
    var assignedToUserID: Int,

    @SerializedName("priority")
    var priority: Int,

    @SerializedName("deadline")
    var deadline: Long,

    @SerializedName("departmentId")
    var departmentID: Int,

    @SerializedName("status")
    var status: Int,
)