package me.hossamohsen.recipeapp.data.network.recipes.responses

import me.hossamohsen.recipeapp.data.models.Category

data class CategoryListResponse(
    val categories: List<Category>
)
