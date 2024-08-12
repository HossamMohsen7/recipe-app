package me.hossamohsen.recipeapp.data.state

sealed class UserState {
    class LoggedIn(val userId: String): UserState()
    data object NotLoggedIn : UserState()
}
