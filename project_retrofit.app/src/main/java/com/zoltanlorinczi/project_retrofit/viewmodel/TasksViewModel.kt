package com.zoltanlorinczi.project_retrofit.viewmodel

import android.app.PendingIntent.getActivity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskDto
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

/**
 * Author:  Zoltan Lorinczi
 * Date:    12/6/2021
 */
class TasksViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var products: MutableLiveData<List<TaskResponse>> = MutableLiveData()
    var ID: Int = 0;

    init {
        getTasks()
    }

    public fun createTask(newTask: TaskDto){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.createTask(it,newTask);
                }
                if (response?.isSuccessful == true) {
                    Log.d("Response", "Get tasks response: ${response.body()}")
                    val toast = Toast.makeText(App.context, "Created a Task", Toast.LENGTH_SHORT)
                    toast.show();
                    getTasks();
                } else {
                    Log.d("Response", "Get tasks error response: ${response?.message()}")
                    val toast = Toast.makeText(App.context, "Error creating a Task", Toast.LENGTH_SHORT)
                    toast.show();
                }

            }catch (e: Exception) {
                Log.d("Response", "TasksViewModel - getTasks() failed with exception: ${e.message}")
                val toast = Toast.makeText(App.context, "Error creating a Task exception", Toast.LENGTH_SHORT)
                toast.show();
            }
        }
    }

    public fun getTasks() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                        SharedPreferencesManager.KEY_TOKEN,
                        "Empty token!"
                )
                val response = token?.let {
                    repository.getTasks(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get tasks response: ${response.body()}")
                    val toast = Toast.makeText(App.context, "Fetched tasks", Toast.LENGTH_SHORT)
                    toast.show();
                    val tasksList = response.body()
                    tasksList?.let {
                        products.value = tasksList
                    }
                } else {
                    Log.d(TAG, "Get tasks error response: ${response?.errorBody()}")
                    val toast = Toast.makeText(App.context, "Cant Fetch tasks", Toast.LENGTH_SHORT)
                    toast.show();
                }

            } catch (e: Exception) {
                val toast = Toast.makeText(App.context, "Cant Fetch tasks exception", Toast.LENGTH_SHORT)
                toast.show();
                Log.d(TAG, "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }
    }
}