package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.ServerResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskDto
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TaskApiService {

    @POST(BackendConstants.CREATE_TASK_URL)
    suspend fun createTask(@Header(BackendConstants.HEADER_TOKEN) token: String,@Body taskDto: TaskDto): Response<ServerResponse>
    @GET(BackendConstants.GET_TASKS_URL)
    suspend fun getTasks(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<TaskResponse>>
}