package me.hossamohsen.recipeapp.data.repository

import me.hossamohsen.recipeapp.data.dao.FavoriteDao
import me.hossamohsen.recipeapp.data.local.AppDatabase
import me.hossamohsen.recipeapp.data.models.FavoriteEntry
import me.hossamohsen.recipeapp.data.models.UserIdAndRecipeId
import me.hossamohsen.recipeapp.data.network.ApiClient
import me.hossamohsen.recipeapp.data.network.recipes.RecipesService
import java.util.UUID

class RecipesRepository(private val recipesService: RecipesService, private val favoriteDao: FavoriteDao) {

    companion object {
        val instance: RecipesRepository by lazy {
            RecipesRepository(ApiClient.recipesService, AppDatabase.getInstance().favoriteDao())
        }
    }

    suspend fun listCategories() = recipesService.listCategories()

    suspend fun searchMeal(name: String) = recipesService.searchMeal(name)

    suspend fun filterByCategory(category: String) = recipesService.filterByCategory(category)

    suspend fun getMealById(id: String) = recipesService.getMealById(id)

    suspend fun listFavoriteRecipes(userId: UUID) = favoriteDao.getEntriesByUser(userId)

    suspend fun addFavoriteRecipe(entry: FavoriteEntry) = favoriteDao.insertEntry(entry)

    suspend fun removeFavoriteRecipe(userId: UUID, recipeId: String) = favoriteDao.deleteByUserIdAndRecipe(
        UserIdAndRecipeId(userId, recipeId)
    )


}