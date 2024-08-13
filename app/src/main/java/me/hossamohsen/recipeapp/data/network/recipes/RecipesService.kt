package me.hossamohsen.recipeapp.data.network.recipes

import me.hossamohsen.recipeapp.data.network.recipes.responses.CategoryListResponse
import me.hossamohsen.recipeapp.data.network.recipes.responses.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {
    @GET("categories.php")
    suspend fun listCategories(): CategoryListResponse

    @GET("search.php")
    suspend fun searchMeal(@Query("s") name: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealsResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealsResponse
}