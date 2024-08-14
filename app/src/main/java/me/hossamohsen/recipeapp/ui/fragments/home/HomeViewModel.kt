package me.hossamohsen.recipeapp.ui.fragments.home

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

class HomeViewModel : ViewModel() {
    private val recipesRepository = RecipesRepository.instance
    private val userRepository = UserRepository.instance

    val isLoadingState = MutableStateFlow(true)
    val categoriesListState = MutableStateFlow(emptyList<Category>())
    val recipesListState = MutableStateFlow(emptyList<Meal>())
    private val selectedCategories = mutableListOf<Category>()

    fun loadCategories() {
        categoriesListState.value = emptyList()
        viewModelScope.launch {
            val categories = recipesRepository.listCategories()
            categoriesListState.value = categories.categories

            if (selectedCategories.isEmpty()) {
                selectedCategories.add(categories.categories.first())
                loadRecipes()
            }
        }
    }

    fun loadRecipes() {
        isLoadingState.value = true
        viewModelScope.launch {
            val favoriteEntries =
                recipesRepository.listFavoriteRecipes(userRepository.currentUser!!.id)
            val recipes = mutableListOf<Meal>()
            selectedCategories.forEach { category ->
                val meals = recipesRepository.filterByCategory(category.category)
                if (meals.meals != null) {
                    recipes.addAll(meals.meals.map {
                        it.copy(
                            category = category.category,
                            isFavorite = favoriteEntries.any { f -> f.recipeId == it.id })
                    })
                }
            }

            recipesListState.value = recipes
            isLoadingState.value = false
        }
    }

    fun addCategoryFilter(category: Category) {
        selectedCategories.add(category)
        loadRecipes()
    }

    fun removeCategoryFilter(category: Category) {
        selectedCategories.remove(category)
        loadRecipes()
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