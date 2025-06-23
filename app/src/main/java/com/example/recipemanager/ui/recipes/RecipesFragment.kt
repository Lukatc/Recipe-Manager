package com.example.recipemanager.ui.recipes

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipemanager.databinding.FragmentRecipesBinding
import com.example.recipemanager.ui.viewmodel.RecipeViewModel

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(
            onRecipeClick = { recipe ->
                val action = RecipesFragmentDirections
                    .actionRecipesToRecipeDetail(recipe.id.toInt())

                findNavController().navigate(action)
            },
            onFavoriteClick = { recipe ->
                viewModel.toggleFavorite(recipe)
            },
            onDeleteClick = { recipe ->
                showDeleteConfirmation(recipe)
            }
        )

        binding.recyclerViewRecipes.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.allRecipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.submitList(recipes)
            binding.textEmptyState.visibility =
                if (recipes.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.totalRecipesCount.observe(viewLifecycleOwner) { count ->
            binding.textRecipeCount.text = "Total Recipes: $count"
        }
    }

    private fun setupClickListeners() {
        binding.fabAddRecipe.setOnClickListener {
            findNavController().navigate(
                RecipesFragmentDirections.actionRecipesToAddRecipe()
            )
        }

        binding.chipFavorites.setOnClickListener {
            viewModel.favoriteRecipes.observe(viewLifecycleOwner) { favorites ->
                recipeAdapter.submitList(favorites)
            }
        }

        binding.chipAll.setOnClickListener {
            viewModel.allRecipes.observe(viewLifecycleOwner) { recipes ->
                recipeAdapter.submitList(recipes)
            }
        }
    }

    private fun showDeleteConfirmation(recipe: com.example.recipemanager.data.database.entities.Recipe) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Recipe")
            .setMessage("Are you sure you want to delete \"${recipe.title}\"?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteRecipe(recipe)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
