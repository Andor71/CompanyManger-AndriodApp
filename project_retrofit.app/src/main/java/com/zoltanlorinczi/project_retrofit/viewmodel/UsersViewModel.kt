package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var users: MutableLiveData<List<UserResponse>> = MutableLiveData()
    var currentUser: MutableLiveData<UserResponse> = MutableLiveData();
    var currentUserID: Int = 0;


    public fun getUsers(){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getUsers(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d("Response Get USer", "Get tasks response: ${response.message()}")
                    val toast = Toast.makeText(App.context, "Fetched tasks", Toast.LENGTH_SHORT)
                    toast.show();
                    val tasksList = response.body()
                    tasksList?.let {
                        users.value = tasksList
                    }
                } else {
                    Log.d("Response Get USer", "Get tasks error response: ${response?.message()}")
                }

            } catch (e: Exception) {
                Log.d(UsersViewModel.TAG, "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }
    }
    public fun getMyUser(){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getMyUser(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(UsersViewModel.TAG, "Get tasks response: ${response.body()}")

                    val user = response.body()
                    user?.let {
                        currentUser.value = user
                    }
                } else {
                    Log.d(UsersViewModel.TAG, "Get tasks error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(UsersViewModel.TAG, "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }

    }

}

