package com.zoltanlorinczi.project_retrofit.api

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
object BackendConstants {

    /**
     * Project backend base URL.
     */
    const val BASE_URL = "http://tracker-3track.a2hosted.com/"

    /**
     * Specific URL segments, which will be concatenated with the base URL.
     */

    const val LOGIN_URL = "login"
    const val GET_TASKS_URL = "task/getTasks"
    const val CREATE_TASK_URL = "task/create"
    const val GET_USERS_URL = "users"
    const val GET_MY_USER = "user"
    const val UPDATE_MY_USER ="user/updateProfile"
    const val GET_GROUPS="department"
    const val GET_ACTIVITES ="/activity/getActivities"
    /**
     * Header values.
     */

    const val HEADER_TOKEN = "token"
}