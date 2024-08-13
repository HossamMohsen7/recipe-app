package me.hossamohsen.recipeapp.ui.fragments.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.local.AppDatabase
import me.hossamohsen.recipeapp.data.local.SharedPreferencesManager
import me.hossamohsen.recipeapp.data.repository.UserRepository
import me.hossamohsen.recipeapp.data.state.UserState
import java.util.UUID

class SplashViewModel : ViewModel() {

    private val userRepository = UserRepository.instance

    fun checkUserLoggedIn(callback: (UserState) -> Unit) {
        viewModelScope.launch {
            delay(3000)
            val loggedUserId = SharedPreferencesManager.getString(SharedPreferencesManager.KEY_USER_ID)
            Log.i("SplashViewModel", "Logged user id: $loggedUserId")
            if (loggedUserId != null) {
                val user = userRepository.getUserById(UUID.fromString(loggedUserId))
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