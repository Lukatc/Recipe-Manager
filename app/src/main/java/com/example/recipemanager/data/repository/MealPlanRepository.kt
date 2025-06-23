package com.example.recipemanager.repository

import com.example.recipemanager.data.database.dao.MealPlanDao
import com.example.recipemanager.data.database.entities.MealPlan
import kotlinx.coroutines.flow.Flow

class MealPlanRepository(private val dao: MealPlanDao) {

    fun getAllMealPlans(): Flow<List<MealPlan>> = dao.getAllMealPlans()

    suspend fun add(meal: MealPlan) = dao.insertMealPlan(meal)

    suspend fun delete(meal: MealPlan) = dao.deleteMealPlan(meal)
}
