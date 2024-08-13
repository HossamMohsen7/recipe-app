package me.hossamohsen.recipeapp.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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

            userRepository.currentUser = user
            callback(true)
        }

    }
}