package me.hossamohsen.recipeapp.data.models

import androidx.room.Entity
import java.util.UUID

@Entity(primaryKeys = ["userId", "recipeId"])
data class FavoriteEntry(
    val userId: UUID,
    val recipeId: String,
    val recipeName: String,
    val recipeCategory: String,
    val recipeThumb: String
)

data class UserIdAndRecipeId(val userId: UUID, val recipeId: String)
