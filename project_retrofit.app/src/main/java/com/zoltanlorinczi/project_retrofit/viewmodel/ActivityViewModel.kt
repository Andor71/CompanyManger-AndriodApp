package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import com.zoltanlorinczi.project_retrofit.api.model.GroupResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var activites: MutableLiveData<List<ActivityResponse>> = MutableLiveData()
    var currentActivity: MutableLiveData<ActivityResponse> = MutableLiveData()

    init {
        getActivites();
    }
    public fun getActivites(){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getActivites(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d("Activites", "Get Activites response: ${response.body()}")
                    val res = response.body()
                    res?.let {
                        activites.value = res
                    }
                } else {
                    Log.d("Activites", "Get Activites error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d("Activites", "Activites - getActivites() failed with exception: ${e.message}")
            }
        }

    }
}
