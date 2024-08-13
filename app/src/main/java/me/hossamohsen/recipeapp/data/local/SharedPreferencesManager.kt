package me.hossamohsen.recipeapp.data.local

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    const val KEY_USER_ID = "user_id"

    private const val PREF_NAME = "recipe_app_pref"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String?) {
        if(value == null) {
            sharedPreferences.edit().remove(key).apply()
            return
        }
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}