package me.hossamohsen.recipeapp.ui.fragments.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.models.Category
import me.hossamohsen.recipeapp.data.models.FavoriteEntry
import me.hossamohsen.recipeapp.data.models.Meal
import me.hossamohsen.recipeapp.data.repository.RecipesRepository
import me.hossamohsen.recipeapp.data.repository.UserRepository

class SearchViewModel : ViewModel() {
    val recipesRepository = RecipesRepository.instance
    val userRepository = UserRepository.instance

    val isLoadingState = MutableStateFlow(true)
    val recipesListState = MutableStateFlow(emptyList<Meal>())
    val noMealsState = MutableStateFlow(false)

    fun searchRecipes(query: String) {
        isLoadingState.value = true

        viewModelScope.launch {
            val favoriteEntries =
                recipesRepository.listFavoriteRecipes(userRepository.currentUser!!.id)
            val recipes = recipesRepository.searchMeal(query)

            if(recipes.meals.isNullOrEmpty()) {
                noMealsState.value = true
                recipesListState.value = emptyList()
            } else {
                noMealsState.value = false
                recipesListState.value = recipes.meals.map {
                    it.copy(isFavorite = favoriteEntries.any { f -> f.recipeId == it.id })
                }.toList()
            }
            isLoadingState.value = false

        }
    }
    fun toggleFavorite(it: Meal, callback: () -> Unit) {
        viewModelScope.launch {
            if (it.isFavorite) {
                recipesRepository.removeFavoriteRecipe(userRepository.currentUser!!.id, it.id)
                it.isFavorite = false
                callback()
            } else {
                recipesRepository.addFavoriteRecipe(
                    FavoriteEntry(
                        userId = userRepository.currentUser!!.id,
                        recipeId = it.id,
                        recipeName = it.name,
                        recipeCategory = it.category ?: "Unknown",
                        recipeThumb = it.thumb
                    )
                )
                it.isFavorite = true
                callback()
            }
        }
    }
}