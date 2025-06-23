package com.example.recipemanager.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.recipemanager.data.database.RecipeDatabase
import com.example.recipemanager.data.database.entities.MealPlan
import com.example.recipemanager.repository.MealPlanRepository
import kotlinx.coroutines.launch

class MealPlanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MealPlanRepository(
        RecipeDatabase.getDatabase(application).mealPlanDao()
    )

    val allMeals: LiveData<List<MealPlan>> =
        repository.getAllMealPlans().asLiveData()

    fun addMeal(meal: MealPlan) = viewModelScope.launch {
        repository.add(meal)
    }

    fun deleteMeal(meal: MealPlan) = viewModelScope.launch {
        repository.delete(meal)
    }
}
