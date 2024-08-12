package me.hossamohsen.recipeapp

import android.app.Application
import me.hossamohsen.recipeapp.data.local.AppDatabase
import me.hossamohsen.recipeapp.data.local.SharedPreferencesManager

class RecipeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
        AppDatabase.init(this)
    }
}