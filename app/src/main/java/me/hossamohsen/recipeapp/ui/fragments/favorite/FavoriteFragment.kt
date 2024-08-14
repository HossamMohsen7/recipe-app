package me.hossamohsen.recipeapp.ui.fragments.favorite

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
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.databinding.FragmentFavoriteBinding
import me.hossamohsen.recipeapp.databinding.FragmentHomeBinding
import me.hossamohsen.recipeapp.ui.adapters.RecipesAdapter
import me.hossamohsen.recipeapp.ui.fragments.search.SearchFragmentDirections

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.isLoadingState.collect {
                _binding?.apply {
                    progressFavoriteRecipes.visibility = if(it) View.VISIBLE else View.GONE
                    rvFavoriteRecipes.visibility = if(it) View.GONE else View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
           viewModel.loadRecipes()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        navController = findNavController()

        val recyclerView = binding.rvFavoriteRecipes
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = RecipesAdapter(emptyList(), {
            navController.navigate(FavoriteFragmentDirections.actionFavoriteFragmentToRecipeDetailFragment(it.id))
        }, {
            viewModel.toggleFavorite(it) {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.recipesListState.collect {
                adapter.setRecipes(it)
                binding.tvNoFavorites.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        return binding.root
    }
}