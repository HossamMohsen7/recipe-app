package me.hossamohsen.recipeapp.ui.fragments.recipedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.models.FavoriteEntry
import me.hossamohsen.recipeapp.data.models.Meal
import me.hossamohsen.recipeapp.data.repository.RecipesRepository
import me.hossamohsen.recipeapp.data.repository.UserRepository

class RecipeDetailViewModel : ViewModel() {
    private val recipesRepository = RecipesRepository.instance
    private val userRepository = UserRepository.instance

    val isLoadingState = MutableStateFlow(true)
    val recipeState = MutableStateFlow<Meal?>(null)

    fun loadRecipe(id: String) {
        isLoadingState.value = true
        viewModelScope.launch {
            val recipe = recipesRepository.getMealById(id);
            val favoriteEntries =
                recipesRepository.listFavoriteRecipes(userRepository.currentUser!!.id)

            if (!recipe.meals.isNullOrEmpty()) {
                val meal = recipe.meals.first()
                meal.isFavorite = favoriteEntries.any { it.recipeId == meal.id }
                recipeState.value = meal
                isLoadingState.value = false
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val meal = recipeState.value
            meal?.let {
                if (it.isFavorite) {
                    recipesRepository.removeFavoriteRecipe(userRepository.currentUser!!.id, it.id)
                    recipeState.value = it.copy(isFavorite = false)
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
                    recipeState.value = it.copy(isFavorite = true)
                }
            }
        }
    }
}