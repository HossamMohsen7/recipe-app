package me.hossamohsen.recipeapp.ui.fragments.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.local.SharedPreferencesManager
import me.hossamohsen.recipeapp.data.repository.UserRepository

class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository.instance
    val errorState = MutableStateFlow("")

    fun login(email: String, password: String, callback: (navigate: Boolean) -> Unit) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                errorState.value = "Please fill all fields"
                callback(false)
                return@launch
            }

            val user = userRepository.getUserByEmailAndPassword(email, password)
            if (user == null) {
                errorState.value = "Invalid email or password"
                callback(false)
                return@launch
            }

            SharedPreferencesManager.saveString(SharedPreferencesManager.KEY_USER_ID, user.id.toString())
            userRepository.currentUser = user
            callback(true)
        }

    }
}