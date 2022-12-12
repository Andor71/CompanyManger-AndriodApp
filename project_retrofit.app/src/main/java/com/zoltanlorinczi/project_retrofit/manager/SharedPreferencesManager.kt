package com.zoltanlorinczi.project_retrofit.manager

import android.content.Context
import android.content.SharedPreferences

/**
 * Contains SharedPreferences related utility methods.
 *
 * Author:  Zoltan Lorinczi
 * Date:    9/8/2021
 */
class SharedPreferencesManager(context: Context) {

    private val SHARED_PREFERENCES_NAME = "ThreeTrackerSharedPreferences"
    private var preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "SHARED_PREFERENCES_KEY_TOKEN"
    }

    fun putStringValue(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getStringValue(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }
}