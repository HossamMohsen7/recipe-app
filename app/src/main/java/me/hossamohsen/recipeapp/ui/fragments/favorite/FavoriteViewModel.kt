package me.hossamohsen.recipeapp.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.models.FavoriteEntry
import me.hossamohsen.recipeapp.data.models.Meal
import me.hossamohsen.recipeapp.data.repository.RecipesRepository
import me.hossamohsen.recipeapp.data.repository.UserRepository

class FavoriteViewModel : ViewModel() {
    private val recipesRepository = RecipesRepository.instance
    private val userRepository = UserRepository.instance

    val isLoadingState = MutableStateFlow(true)
    val recipesListState = MutableStateFlow(emptyList<Meal>())

    fun loadRecipes() {
        isLoadingState.value = true
        viewModelScope.launch {
            val recipes = recipesRepository.listFavoriteRecipes(userRepository.currentUser!!.id)
            recipesListState.value = recipes.map {
                Meal(
                    id = it.recipeId,
                    name = it.recipeName,
                    category = it.recipeCategory,
                    thumb = it.recipeThumb,
                    isFavorite = true
                )
            }.toList()
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