package me.hossamohsen.recipeapp.ui.fragments.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.data.models.Category
import me.hossamohsen.recipeapp.data.models.Meal
import me.hossamohsen.recipeapp.data.repository.RecipesRepository

class SearchViewModel : ViewModel() {
    val recipesRepository = RecipesRepository.instance

    val isLoadingState = MutableStateFlow(true)
    val recipesListState = MutableStateFlow(emptyList<Meal>())
    val noMealsState = MutableStateFlow(false)

    fun searchRecipes(query: String) {
        isLoadingState.value = true

        viewModelScope.launch {
            val recipes = recipesRepository.searchMeal(query)
            Log.i("SearchViewModel", "searchRecipes: $recipes")

            if(recipes.meals.isNullOrEmpty()) {
                noMealsState.value = true
                recipesListState.value = emptyList()
            } else {
                noMealsState.value = false
                recipesListState.value = recipes.meals
            }
            isLoadingState.value = false

        }
    }
}