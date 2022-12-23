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
    var createTaskIsSuccess: MutableLiveData<Boolean> = MutableLiveData();
    var ID: Int = 0;


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
                    getTasks();
                    createTaskIsSuccess.value = true;
                } else {
                    createTaskIsSuccess.value = false;
                    Log.d("Task","Error")
                }

            }catch (e: Exception) {
                Log.d("Task","${e.message}")
                createTaskIsSuccess.value = false;
            }
        }
        createTaskIsSuccess.value = null;
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
                    val tasksList = response.body()
                    tasksList?.let {
                        products.value = tasksList
                    }
                } else {
                        products.value = emptyList();
                }

            } catch (e: Exception) {
                products.value = null;
            }
        }
    }
}