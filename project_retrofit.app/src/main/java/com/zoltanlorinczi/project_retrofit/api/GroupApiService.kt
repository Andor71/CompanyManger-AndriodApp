package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.GroupResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserUpdateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GroupApiService {

    @GET(BackendConstants.GET_GROUPS)
    suspend fun getGroups(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<GroupResponse>>
}