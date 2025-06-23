package com.example.recipemanager.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.recipemanager.data.database.entities.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes WHERE userId = :userId ORDER BY dateCreated DESC")
    fun getAllRecipes(userId: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE category = :category AND userId = :userId ORDER BY title ASC")
    fun getRecipesByCategory(category: String, userId: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 AND userId = :userId ORDER BY title ASC")
    fun getFavoriteRecipes(userId: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :searchQuery || '%' AND userId = :userId")
    fun searchRecipes(searchQuery: String, userId: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): Recipe?

    @Insert
    suspend fun insertRecipe(recipe: Recipe): Long

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT COUNT(*) FROM recipes WHERE userId = :userId")
    fun getTotalRecipesCount(userId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM recipes WHERE isFavorite = 1 AND userId = :userId")
    fun getFavoriteRecipesCount(userId: String): Flow<Int>
}
