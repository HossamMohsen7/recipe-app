package me.hossamohsen.recipeapp.data.repository

import me.hossamohsen.recipeapp.data.dao.UserDao
import me.hossamohsen.recipeapp.data.local.AppDatabase
import me.hossamohsen.recipeapp.data.models.User
import java.util.UUID

class UserRepository(private val userDao: UserDao) {

    var currentUser: User? = null

    companion object {
        val instance: UserRepository by lazy {
            UserRepository(AppDatabase.getInstance().userDao())
        }
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(id: UUID): User? {
        return userDao.getUserById(id)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }


}