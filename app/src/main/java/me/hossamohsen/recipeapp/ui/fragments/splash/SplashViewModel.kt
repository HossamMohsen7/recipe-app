package me.hossamohsen.recipeapp.ui.fragments.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.local.AppDatabase
import me.hossamohsen.recipeapp.data.local.SharedPreferencesManager
import me.hossamohsen.recipeapp.data.repository.UserRepository
import me.hossamohsen.recipeapp.data.state.UserState

class SplashViewModel : ViewModel() {

    private val userRepository = UserRepository(AppDatabase.getInstance().userDao())

    fun checkUserLoggedIn(callback: (UserState) -> Unit) {
        viewModelScope.launch {
            delay(3000)
            val loggedUserId = SharedPreferencesManager.getString(SharedPreferencesManager.KEY_USER_ID)
            if (loggedUserId != null) {
                val user = userRepository.getUserById(loggedUserId)
                if (user == null) {
                    SharedPreferencesManager.saveString(SharedPreferencesManager.KEY_USER_ID, null)
                    callback(UserState.NotLoggedIn)
                    return@launch
                }

                userRepository.currentUser = user
                callback(UserState.LoggedIn(loggedUserId))
            } else {
                callback(UserState.NotLoggedIn)
            }
        }
    }
}