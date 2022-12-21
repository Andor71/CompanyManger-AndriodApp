package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserUpdateDto
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var users: MutableLiveData<List<UserResponse>> = MutableLiveData()
    var currentUser: MutableLiveData<UserResponse> = MutableLiveData();
    var myUser: MutableLiveData<UserResponse> = MutableLiveData();
    var loggedIn: MutableLiveData<Boolean> = MutableLiveData();

    init {
        getUsers();
    }
    public fun getUsers(){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    Log.i("Token",it);
                    repository.getUsers(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d("ResponseUser", "Get Users response: ${response.message()}")

                    val toast = Toast.makeText(App.context, "Fetched Users", Toast.LENGTH_SHORT)
                    toast.show();
                    val userList = response.body()
                    userList?.let {
                        users.value = userList
                    }
                } else {

                    val toast = Toast.makeText(App.context, "Cant Fetch Users", Toast.LENGTH_SHORT)
                    toast.show();
                    Log.d("ResponseUser", "Users Error Response : ${response?.message()}")
                }

            } catch (e: Exception) {
                Log.d("ResponseUser", "UserViewmodel get users error: ${e.message}")
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
                    Log.d("USER", "Get MyUserResponse response: ${response.body()}")
                    loggedIn.value = true;
                    val user = response.body()
                    user?.let {
                        myUser.value = user
                    }
                } else {
                    loggedIn.value = false;
                    Log.d("USER", "Get tasks error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(UsersViewModel.TAG, "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }

    }
    public fun updateMyProfile(userUpdateDto: UserUpdateDto){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.updateMyUser(it,userUpdateDto);
                }

                if (response?.isSuccessful == true) {
                    Log.d(UsersViewModel.TAG, "Get tasks response: ${response.body()}")
                    val toast = Toast.makeText(App.context, "Profile updated succesfully!", Toast.LENGTH_SHORT)
                    toast.show();
//                    val user = response.body()
//                    user?.let {
//                        currentUser.value = user;
//                    }
                } else {
                    val toast = Toast.makeText(App.context, "Error updating profile", Toast.LENGTH_SHORT)
                    toast.show();
                    Log.d(UsersViewModel.TAG, "Get tasks error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(UsersViewModel.TAG, "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }
    }

}

