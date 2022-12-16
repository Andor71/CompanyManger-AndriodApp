package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit web service API.
 *
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
interface UserApiService {

    @POST(BackendConstants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequestBody): Response<LoginResponse>

    @GET(BackendConstants.GET_USERS_URL)
    suspend fun getUsers(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<UserResponse>>

    @GET(BackendConstants.GET_MY_USER)
    suspend fun getMyUser(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<UserResponse>
    @GET(BackendConstants.UPDATE_MY_USER)
    suspend fun updateMyUser(token: String, userUpdateDto: UserUpdateDto): Response<Any>
}