package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserUpdateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ActivityApiService {
    @GET(BackendConstants.GET_ACTIVITES)
    suspend fun getActivites(@Header(BackendConstants.HEADER_TOKEN) token: String ): Response<List<ActivityResponse>>
}