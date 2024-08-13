package me.hossamohsen.recipeapp.data.network

import me.hossamohsen.recipeapp.data.network.recipes.RecipesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private val retrofit by lazy { getClient() }
    val recipesService: RecipesService by lazy { retrofit.create(RecipesService::class.java) }

    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}