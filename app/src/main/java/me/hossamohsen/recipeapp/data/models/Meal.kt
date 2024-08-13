package me.hossamohsen.recipeapp.data.models

import com.google.gson.annotations.SerializedName

data class Meal
    (
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMealThumb") val thumb: String,
    @SerializedName("strMeal") val name: String,

    @SerializedName("strDrinkAlternate") val drinkAlternate: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strTags") val tags: String?,
    @SerializedName("strYoutube") val youtube: String?,
    @Volatile var isFavorite: Boolean = false
)
