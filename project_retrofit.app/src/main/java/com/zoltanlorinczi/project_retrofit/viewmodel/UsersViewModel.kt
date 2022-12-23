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
    var updateProfileIsSuccesful: MutableLiveData<Boolean> = MutableLiveData();
    var getUsersIsSuccesful: MutableLiveData<Boolean> = MutableLiveData();
    var getMyUserIsSuccesful: MutableLiveData<Boolean> = MutableLiveData();

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
                    getUsersIsSuccesful.value = true;
                    val userList = response.body()
                    userList?.let {
                        users.value = userList
                    }
                } else {
                    getUsersIsSuccesful.value = false;
                    users.value = emptyList();
                }

            } catch (e: Exception) {
                Log.d("ResponseUser", "UserViewmodel get users error: ${e.message}")
                getUsersIsSuccesful.value = false;
                users.value = emptyList();
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
                    getMyUserIsSuccesful.value = true;
                    loggedIn.value = true;
                    val user = response.body()
                    user?.let {
                        myUser.value = user
                    }
                } else {
                    getMyUserIsSuccesful.value = false;
                    loggedIn.value = false;
                    myUser.value = UserResponse();
                }

            } catch (e: Exception) {
                getMyUserIsSuccesful.value = false;
                loggedIn.value = false;
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
                    val toast = Toast.makeText(App.context, "Profile updated succesfully!", Toast.LENGTH_SHORT)
                    toast.show();
                    getMyUser();
                } else {
                    val toast = Toast.makeText(App.context, "Error updating profile", Toast.LENGTH_SHORT)
                    toast.show();
                    updateProfileIsSuccesful.value =false;
                }

            } catch (e: Exception) {
                val toast = Toast.makeText(App.context, "Error updating profile", Toast.LENGTH_SHORT)
                toast.show();
                updateProfileIsSuccesful.value = false;
            }
            updateProfileIsSuccesful.value =null;
        }
    }

}

