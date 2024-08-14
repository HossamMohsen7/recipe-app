package me.hossamohsen.recipeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.hossamohsen.recipeapp.data.models.FavoriteEntry
import me.hossamohsen.recipeapp.data.models.User
import me.hossamohsen.recipeapp.data.models.UserIdAndRecipeId
import java.util.UUID

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertEntry(entry: FavoriteEntry)

    @Update
    suspend fun updateEntry(entry: FavoriteEntry)

    @Query("SELECT * FROM favoriteentry WHERE userId = :userId AND recipeId = :recipeId")
    suspend fun getEntryByUserAndRecipe(userId: UUID, recipeId: String): List<FavoriteEntry>

    @Query("SELECT * FROM favoriteentry WHERE userId = :userId")
    suspend fun getEntriesByUser(userId: UUID): List<FavoriteEntry>

    @Delete(entity = FavoriteEntry::class)
    suspend fun deleteByUserIdAndRecipe(vararg idRecipe: UserIdAndRecipeId)

}