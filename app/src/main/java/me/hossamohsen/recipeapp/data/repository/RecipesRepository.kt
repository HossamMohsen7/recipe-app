package me.hossamohsen.recipeapp.data.repository

import me.hossamohsen.recipeapp.data.network.ApiClient
import me.hossamohsen.recipeapp.data.network.recipes.RecipesService

class RecipesRepository(private val recipesService: RecipesService) {

    companion object {
        val instance: RecipesRepository by lazy {
            RecipesRepository(ApiClient.recipesService)
        }
    }

    suspend fun listCategories() = recipesService.listCategories()

    suspend fun searchMeal(name: String) = recipesService.searchMeal(name)

    suspend fun filterByCategory(category: String) = recipesService.filterByCategory(category)

    suspend fun getMealById(id: String) = recipesService.getMealById(id)

}