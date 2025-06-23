package com.example.recipemanager.ui.mealplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.databinding.ItemMealPlanBinding
import com.example.recipemanager.data.database.entities.MealPlan

class MealPlanAdapter(
    private val onDelete: (MealPlan) -> Unit
) : RecyclerView.Adapter<MealPlanAdapter.MealViewHolder>() {

    private val meals = mutableListOf<MealPlan>()

    fun submitList(newMeals: List<MealPlan>) {
        meals.clear()
        meals.addAll(newMeals)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount() = meals.size

    inner class MealViewHolder(private val binding: ItemMealPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: MealPlan) {
            binding.textMealTitle.text = meal.title
            binding.btnDeleteMeal.setOnClickListener { onDelete(meal) }
        }
    }
}
