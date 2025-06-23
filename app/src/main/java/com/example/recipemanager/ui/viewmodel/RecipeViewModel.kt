package com.example.recipemanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.recipemanager.data.database.RecipeDatabase
import com.example.recipemanager.data.database.entities.Ingredient
import com.example.recipemanager.data.database.entities.Recipe
import com.example.recipemanager.data.repository.RecipeRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository
    private val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    init {
        val db = RecipeDatabase.getDatabase(application)
        repository = RecipeRepository(
            db.recipeDao(),
            db.ingredientDao(),
            db.mealPlanDao()
        )
    }

    val allRecipes: LiveData<List<Recipe>> = repository.getAllRecipes(userId).asLiveData()
    val favoriteRecipes: LiveData<List<Recipe>> = repository.getFavoriteRecipes(userId).asLiveData()
    val totalRecipesCount: LiveData<Int> = repository.getTotalRecipesCount(userId).asLiveData()

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.deleteIngredientsForRecipe(recipe.id)
        repository.deleteRecipe(recipe)
    }


    fun getRecipeById(id: Long): LiveData<Recipe?> = liveData {
        emit(repository.getRecipeById(id))
    }

    fun getIngredientsByRecipeId(id: Long): LiveData<List<Ingredient>> {
        return repository.getIngredientsForRecipe(id).asLiveData()
    }

    fun insertRecipe(recipe: Recipe, ingredients: List<Ingredient>) = viewModelScope.launch {
        val recipeWithUser = recipe.copy(userId = userId)
        val recipeId = repository.insertRecipe(recipeWithUser)
        val ingredientsWithId = ingredients.map { it.copy(recipeId = recipeId) }
        repository.insertIngredients(ingredientsWithId)
    }

    fun toggleFavorite(recipe: Recipe) = viewModelScope.launch {
        repository.updateRecipe(recipe.copy(isFavorite = !recipe.isFavorite))
    }
}
