package me.hossamohsen.recipeapp.ui.fragments.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.databinding.FragmentHomeBinding
import me.hossamohsen.recipeapp.databinding.FragmentLoginBinding
import me.hossamohsen.recipeapp.ui.adapters.RecipesAdapter

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.isLoadingState.collect {
                _binding?.apply {
                    progressHomeRecipes.visibility = if(it) View.VISIBLE else View.GONE
                    rvHomeRecipes.visibility = if(it) View.GONE else View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.categoriesListState.collect {
                _binding?.apply {
                   val chipGroup = binding.homeChipGroup
                    chipGroup.removeAllViews()
                    it.forEachIndexed { i, category ->
                        val chip = Chip(chipGroup.context)
                        chip.text = category.category
                        chip.isCheckable = true
                        if(i == 0) {
                            chip.isChecked = true
                        }
                        chipGroup.addView(chip)
                        chip.setOnClickListener {
                            if(chip.isChecked) {
                                viewModel.addCategoryFilter(category)
                            } else {
                                viewModel.removeCategoryFilter(category)
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loadCategories()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()


        val recyclerView = binding.rvHomeRecipes
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = RecipesAdapter(emptyList(), {}, {})
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.recipesListState.collect {
                adapter.setRecipes(it)
            }
        }

        return binding.root
    }
}