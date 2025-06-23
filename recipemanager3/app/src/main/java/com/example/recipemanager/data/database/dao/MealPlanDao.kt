package com.example.recipemanager.data.database.dao

import androidx.room.*
import com.example.recipemanager.data.database.entities.MealPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {

    @Query("SELECT * FROM meal_plan")
    fun getAllMealPlans(): Flow<List<MealPlan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlan(mealPlan: MealPlan): Long

    @Delete
    suspend fun deleteMealPlan(mealPlan: MealPlan)
}

