package me.hossamohsen.recipeapp.data.models

import com.google.gson.annotations.SerializedName

data class Meal
    (
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMealThumb") val thumb: String,
    @SerializedName("strMeal") val name: String,

    @SerializedName("strDrinkAlternate") val drinkAlternate: String? = null,
    @SerializedName("strCategory") val category: String? = null,
    @SerializedName("strArea") val area: String? = null,
    @SerializedName("strInstructions") val instructions: String? = null,
    @SerializedName("strTags") val tags: String? = null,
    @SerializedName("strYoutube") val youtube: String? = null,
    @Volatile var isFavorite: Boolean = false
)
