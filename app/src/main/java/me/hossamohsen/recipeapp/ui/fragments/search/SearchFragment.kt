package me.hossamohsen.recipeapp.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.databinding.FragmentSearchBinding
import me.hossamohsen.recipeapp.ui.adapters.RecipesAdapter
import me.hossamohsen.recipeapp.utils.DebouncingTextWatcher


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.isLoadingState.collect {
                _binding?.apply {
                    if(it) {
                        tvStartTyping.visibility = View.GONE
                    }
                    progressSearchRecipes.visibility = if (it) View.VISIBLE else View.GONE
                    rvSearchRecipes.visibility = if (it) View.GONE else View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.noMealsState.collect {
                _binding?.apply {
                    tvNoMeals.visibility = if (it) View.VISIBLE else View.GONE
                    rvSearchRecipes.visibility = if (it) View.GONE else View.VISIBLE
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val recyclerView = binding.rvSearchRecipes
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = RecipesAdapter(emptyList(), {
            navController.navigate(SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(it.id))
        }, {
            viewModel.toggleFavorite(it) {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.recipesListState.collect {
                adapter.setRecipes(it)
            }
        }

        binding.apply {
            etRecipeSearch.addTextChangedListener(DebouncingTextWatcher(lifecycle) { query ->
                query?.let {
                    viewModel.searchRecipes(query)
                }
            })
        }

        return binding.root
    }
}