package me.hossamohsen.recipeapp.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.models.User
import me.hossamohsen.recipeapp.data.repository.UserRepository
import java.util.UUID

class RegisterViewModel : ViewModel() {
    private val userRepository = UserRepository.instance
    val errorState = MutableStateFlow("")

    fun register(fullName: String, email: String, password: String, callback: (navigate: Boolean) -> Unit) {
        viewModelScope.launch {
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorState.value = "Please fill all fields"
                callback(false)
                return@launch
            }

            val user = userRepository.getUserByEmail(email)
            if (user != null) {
                errorState.value = "Email already exists"
                callback(false)
                return@launch
            }

            userRepository.insertUser(User(UUID.randomUUID(), fullName, email, password))
            callback(true)
        }

    }
}