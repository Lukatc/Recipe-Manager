package com.example.recipemanager.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val instructions: String,
    val prepTimeMinutes: Int = 0,
    val cookTimeMinutes: Int = 0,
    val servings: Int = 1,
    val difficulty: String = "Easy", // Easy, Medium, Hard
    val category: String = "Main Course", // Breakfast, Lunch, Dinner, Snack, Dessert
    val imageUrl: String = "",
    val isFavorite: Boolean = false,
    val dateCreated: Long = System.currentTimeMillis(),
    val userId: String = ""
) {
    val totalTimeMinutes: Int
        get() = prepTimeMinutes + cookTimeMinutes
}