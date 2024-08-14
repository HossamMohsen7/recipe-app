package me.hossamohsen.recipeapp.data.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strCategoryThumb") val thumb: String,
    @SerializedName("strCategoryDescription") val description: String
)
