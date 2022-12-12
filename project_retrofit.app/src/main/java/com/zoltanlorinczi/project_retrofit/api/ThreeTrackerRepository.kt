package com.zoltanlorinczi.project_retrofit.api

import RetrofitInstance
import com.zoltanlorinczi.project_retrofit.api.model.*
import retrofit2.Response

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
class ThreeTrackerRepository {

    /**
     * 'suspend' keyword means that this function can be blocking.
     * We need to be aware that we can only call them from within a coroutine or an other suspend function!
     */
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponse> {
        return RetrofitInstance.USER_API_SERVICE.login(loginRequestBody)
    }

    suspend fun getTasks(token: String): Response<List<TaskResponse>> {
        return RetrofitInstance.TASK_API_SERVICE.getTasks(token)
    }

    suspend fun createTask(token:String,taskDto: TaskDto): Response<ServerResponse>{
        return RetrofitInstance.TASK_API_SERVICE.createTask(token,taskDto);
    }

    suspend fun getUsers(token: String):Response<List<UserResponse>>{
        return RetrofitInstance.USER_API_SERVICE.getUsers(token);
    }
    suspend fun getMyUser(token: String):Response<UserResponse>{
        return RetrofitInstance.USER_API_SERVICE.getMyUser(token);
    }

}