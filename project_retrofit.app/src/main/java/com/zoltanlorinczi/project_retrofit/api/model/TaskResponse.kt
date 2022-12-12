package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
data class TaskResponse(
    @SerializedName("ID")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("created_time")
    var createdTime: Long,

    @SerializedName("created_by_user_ID")
    var createdByUserID: Int,

    @SerializedName("asigned_to_user_ID")
    var assignedToUserID: Int,

    @SerializedName("priority")
    var priority: Int,

    @SerializedName("deadline")
    var deadline: Long,

    @SerializedName("department_ID")
    var departmentID: Int,

    @SerializedName("status")
    var status: Int,

    @SerializedName("progress")
    var progress: String

)
