package com.example.recipemanager.data.repository

import com.example.recipemanager.data.database.dao.RecipeDao
import com.example.recipemanager.data.database.dao.IngredientDao
import com.example.recipemanager.data.database.dao.MealPlanDao
import com.example.recipemanager.data.database.entities.Recipe
import com.example.recipemanager.data.database.entities.Ingredient
import com.example.recipemanager.data.database.entities.MealPlan
import kotlinx.coroutines.flow.Flow

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val mealPlanDao: MealPlanDao
) {

    // Recipe operations
    fun getAllRecipes(userId: String): Flow<List<Recipe>> = recipeDao.getAllRecipes(userId)

    fun getRecipesByCategory(category: String, userId: String): Flow<List<Recipe>> =
        recipeDao.getRecipesByCategory(category, userId)

    fun getFavoriteRecipes(userId: String): Flow<List<Recipe>> =
        recipeDao.getFavoriteRecipes(userId)

    fun searchRecipes(searchQuery: String, userId: String): Flow<List<Recipe>> =
        recipeDao.searchRecipes(searchQuery, userId)

    suspend fun insertRecipe(recipe: Recipe): Long = recipeDao.insertRecipe(recipe)

    suspend fun updateRecipe(recipe: Recipe) = recipeDao.updateRecipe(recipe)

    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)

    suspend fun getRecipeById(recipeId: Long): Recipe? = recipeDao.getRecipeById(recipeId)

    fun getTotalRecipesCount(userId: String): Flow<Int> = recipeDao.getTotalRecipesCount(userId)

    fun getFavoriteRecipesCount(userId: String): Flow<Int> = recipeDao.getFavoriteRecipesCount(userId)

    // Ingredient operations
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<Ingredient>> =
        ingredientDao.getIngredientsForRecipe(recipeId)

    suspend fun insertIngredients(ingredients: List<Ingredient>) =
        ingredientDao.insertIngredients(ingredients)

    suspend fun deleteIngredientsForRecipe(recipeId: Long) =
        ingredientDao.deleteIngredientsForRecipe(recipeId)

    // MealPlan operations
    fun getAllMealPlans(): Flow<List<MealPlan>> =
        mealPlanDao.getAllMealPlans()

    suspend fun insertMealPlan(mealPlan: MealPlan): Long = mealPlanDao.insertMealPlan(mealPlan)

    suspend fun deleteMealPlan(mealPlan: MealPlan) = mealPlanDao.deleteMealPlan(mealPlan)
}
