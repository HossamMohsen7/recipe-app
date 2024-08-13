package me.hossamohsen.recipeapp.ui.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.models.Category
import me.hossamohsen.recipeapp.data.models.Meal
import me.hossamohsen.recipeapp.data.repository.RecipesRepository

class HomeViewModel : ViewModel() {
    private val recipesRepository = RecipesRepository.instance

    val isLoadingState = MutableStateFlow(true)
    val categoriesListState = MutableStateFlow(emptyList<Category>())
    val recipesListState = MutableStateFlow(emptyList<Meal>())
    private val selectedCategories = mutableListOf<Category>()

    fun loadCategories() {
        categoriesListState.value = emptyList()
        viewModelScope.launch {
            val categories = recipesRepository.listCategories()
            categoriesListState.value = categories.categories

            if(selectedCategories.isEmpty()) {
                selectedCategories.add(categories.categories.first())
                loadRecipes()
            }
        }
    }

    fun loadRecipes() {
        isLoadingState.value = true
        viewModelScope.launch {
            val recipes = mutableListOf<Meal>()
            selectedCategories.forEach { category ->
                val meals = recipesRepository.filterByCategory(category.category)
                if(meals.meals != null) {
                    recipes.addAll(meals.meals.map { it.copy(category = category.category) })
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
}