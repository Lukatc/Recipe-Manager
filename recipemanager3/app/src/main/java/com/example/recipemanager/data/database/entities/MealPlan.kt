package com.example.recipemanager.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_plan")
data class MealPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)
