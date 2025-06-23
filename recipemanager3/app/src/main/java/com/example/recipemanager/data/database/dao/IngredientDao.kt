package com.example.recipemanager.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.recipemanager.data.database.entities.Ingredient

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId ORDER BY name ASC")
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<Ingredient>>

    @Insert
    suspend fun insertIngredient(ingredient: Ingredient): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<Ingredient>)

    @Update
    suspend fun updateIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("DELETE FROM ingredients WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsForRecipe(recipeId: Long)
}
