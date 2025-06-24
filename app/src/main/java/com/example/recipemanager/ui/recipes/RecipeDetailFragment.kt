package com.example.recipemanager.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.recipemanager.data.database.RecipeDatabase
import com.example.recipemanager.data.repository.RecipeRepository
import com.example.recipemanager.databinding.FragmentRecipeDetailBinding
import com.example.recipemanager.ui.viewmodel.RecipeViewModel 
import com.example.recipemanager.ui.viewmodel.RecipeViewModelFactory 

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecipeViewModel
    private val args: RecipeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = RecipeDatabase.getDatabase(requireContext())
        val repository = RecipeRepository(
            database.recipeDao(),
            database.ingredientDao(),
            database.mealPlanDao()
        )
        val factory = RecipeViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        val recipeId = args.recipeId.toLong()

        viewModel.getRecipeById(recipeId).observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                binding.textTitle.text = it.title
                binding.textDescription.text = it.description
                binding.textInstructions.text = it.instructions
                binding.textTime.text = "${it.totalTimeMinutes} min"
                binding.textServings.text = "${it.servings} servings"
                binding.textCategory.text = it.category
                binding.textDifficulty.text = it.difficulty
            }
        }

        viewModel.getIngredientsByRecipeId(recipeId).observe(viewLifecycleOwner) { list ->
            val details = list.joinToString("\n") { i ->
                "${i.amount} ${i.unit} ${i.name}"
            }
            binding.textIngredients.text = details
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
