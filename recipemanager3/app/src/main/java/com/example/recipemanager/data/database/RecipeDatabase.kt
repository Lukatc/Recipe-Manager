package com.example.recipemanager.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.recipemanager.data.database.entities.Recipe
import com.example.recipemanager.data.database.entities.Ingredient
import com.example.recipemanager.data.database.entities.MealPlan
import com.example.recipemanager.data.database.dao.RecipeDao
import com.example.recipemanager.data.database.dao.IngredientDao
import com.example.recipemanager.data.database.dao.MealPlanDao

@Database(
    entities = [Recipe::class, Ingredient::class, MealPlan::class],
    version = 2,
    exportSchema = false
)

abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun mealPlanDao(): MealPlanDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
