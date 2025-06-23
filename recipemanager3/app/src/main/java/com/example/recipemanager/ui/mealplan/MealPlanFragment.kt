package com.example.recipemanager.ui.mealplan

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipemanager.databinding.FragmentMealPlanBinding
import com.example.recipemanager.data.database.entities.MealPlan
import com.example.recipemanager.viewmodel.MealPlanViewModel
import com.example.recipemanager.ui.viewmodel.RecipeViewModel

class MealPlanFragment : Fragment() {

    private var _binding: FragmentMealPlanBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by activityViewModels()
    private val mealPlanViewModel: MealPlanViewModel by activityViewModels()

    private lateinit var adapter: MealPlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MealPlanAdapter { meal: MealPlan ->
            mealPlanViewModel.deleteMeal(meal)
        }

        binding.recyclerMealPlan.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMealPlan.adapter = adapter

        mealPlanViewModel.allMeals.observe(viewLifecycleOwner) { meals: List<MealPlan> ->
            adapter.submitList(meals)
        }

        binding.btnAddMeal.setOnClickListener {
            recipeViewModel.allRecipes.observe(viewLifecycleOwner) { recipes ->
                if (recipes.isNullOrEmpty()) return@observe

                val titles = recipes.map { it.title }
                AlertDialog.Builder(requireContext())
                    .setTitle("Choose a recipe")
                    .setItems(titles.toTypedArray()) { _: android.content.DialogInterface, which: Int ->
                        val selected = recipes[which]
                        mealPlanViewModel.addMeal(MealPlan(title = selected.title))
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
