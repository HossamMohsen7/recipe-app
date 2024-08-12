package me.hossamohsen.recipeapp.data.repository

import me.hossamohsen.recipeapp.data.dao.UserDao
import me.hossamohsen.recipeapp.data.models.User

class UserRepository(private val userDao: UserDao) {

    var currentUser: User? = null

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(id: String): User? {
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