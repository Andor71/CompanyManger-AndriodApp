package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.GroupResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class GroupViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var groups: MutableLiveData<List<GroupResponse>> = MutableLiveData()
    var ID: Int = 0;
    init {
        fetchGroups();
    }

    public fun fetchGroups(){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getGroups(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(GroupViewModel.TAG, "Get group response: ${response.body()}")
                    val toast = Toast.makeText(App.context, "Fetched groups", Toast.LENGTH_SHORT)
                    toast.show();
                    val groupList = response.body()
                    groupList?.let {
                        groups.value = groupList
                    }
                } else {
                    Log.d(GroupViewModel.TAG, "Get tasks error response: ${response?.errorBody()}")
                    val toast = Toast.makeText(App.context, "Cant Fetch Group", Toast.LENGTH_SHORT)
                    toast.show();
                }

            } catch (e: Exception) {
                val toast = Toast.makeText(App.context, "Cant Fetch Groups exception", Toast.LENGTH_SHORT)
                toast.show();
                Log.d(GroupViewModel.TAG, "GroupViewMOdel - getGroups() failed with exception: ${e.message}")
            }
        }
    }

}