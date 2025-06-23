package com.example.recipemanager.ui.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemanager.data.database.entities.Recipe
import com.example.recipemanager.databinding.ItemRecipeBinding

class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit,
    private val onFavoriteClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit


) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.textTitle.text = recipe.title
            binding.textCategory.text = recipe.category
            binding.textTime.text = "${recipe.totalTimeMinutes} min"
            binding.buttonFavorite.setImageResource(
                if (recipe.isFavorite) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )

            binding.root.setOnClickListener { onRecipeClick(recipe) }
            binding.buttonFavorite.setOnClickListener { onFavoriteClick(recipe) }
            binding.buttonDelete.setOnClickListener { onDeleteClick(recipe) } // ✅ delete handler
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem == newItem
        }
    }
}
