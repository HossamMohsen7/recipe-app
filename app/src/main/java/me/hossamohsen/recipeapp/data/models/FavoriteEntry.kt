package me.hossamohsen.recipeapp.data.models

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "recipeId"])
data class FavoriteEntry(
    val userId: Int,
    val recipeId: Int,
    val recipeName: String,
    val recipeCategory: String,
    val recipeThumb: String
)
