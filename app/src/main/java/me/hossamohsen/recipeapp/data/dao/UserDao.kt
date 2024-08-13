package me.hossamohsen.recipeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.hossamohsen.recipeapp.data.models.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>
}