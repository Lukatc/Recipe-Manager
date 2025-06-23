package com.example.recipemanager.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipemanager.R
import com.example.recipemanager.data.database.entities.Recipe
import com.example.recipemanager.data.database.entities.Ingredient
import com.example.recipemanager.databinding.FragmentAddRecipeBinding
import com.example.recipemanager.ui.viewmodel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class AddRecipeFragment : Fragment() {

    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels()
    private val ingredients = mutableListOf<Ingredient>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinners()
        setupClickListeners()
    }

    private fun setupSpinners() {
        val categories = arrayOf("Breakfast", "Lunch", "Dinner", "Snack", "Dessert")
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = categoryAdapter

        val difficulties = arrayOf("Easy", "Medium", "Hard")
        val difficultyAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, difficulties)
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDifficulty.adapter = difficultyAdapter
    }

    private fun setupClickListeners() {
        binding.buttonAddIngredient.setOnClickListener {
            addIngredient()
        }

        binding.buttonSaveRecipe.setOnClickListener {
            saveRecipe()
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun addIngredient() {
        val name = binding.editIngredientName.text.toString().trim()
        val amount = binding.editIngredientAmount.text.toString().trim()
        val unit = binding.editIngredientUnit.text.toString().trim()

        if (name.isNotEmpty()) {
            val ingredient = Ingredient(
                recipeId = 0,
                name = name,
                amount = amount,
                unit = unit
            )
            ingredients.add(ingredient)

            binding.editIngredientName.text?.clear()
            binding.editIngredientAmount.text?.clear()
            binding.editIngredientUnit.text?.clear()

            updateIngredientsDisplay()
        }
    }

    private fun updateIngredientsDisplay() {
        val ingredientsText = ingredients.joinToString("\n") { ingredient ->
            "${ingredient.amount} ${ingredient.unit} ${ingredient.name}".trim()
        }
        binding.textIngredientsPreview.text = ingredientsText
    }

    private fun saveRecipe() {
        val title = binding.editRecipeTitle.text.toString().trim()
        val description = binding.editRecipeDescription.text.toString().trim()
        val instructions = binding.editInstructions.text.toString().trim()
        val prepTime = binding.editPrepTime.text.toString().toIntOrNull() ?: 0
        val cookTime = binding.editCookTime.text.toString().toIntOrNull() ?: 0
        val servings = binding.editServings.text.toString().toIntOrNull() ?: 1
        val category = binding.spinnerCategory.selectedItem.toString()
        val difficulty = binding.spinnerDifficulty.selectedItem.toString()

        if (title.isEmpty() || instructions.isEmpty()) {
            Snackbar.make(binding.root, "Please fill in required fields", Snackbar.LENGTH_SHORT).show()
            return
        }

        val recipe = Recipe(
            title = title,
            description = description,
            instructions = instructions,
            prepTimeMinutes = prepTime,
            cookTimeMinutes = cookTime,
            servings = servings,
            category = category,
            difficulty = difficulty
        )

        viewModel.insertRecipe(recipe, ingredients)

        Snackbar.make(binding.root, "Recipe saved successfully!", Snackbar.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
