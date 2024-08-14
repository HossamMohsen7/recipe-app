package me.hossamohsen.recipeapp.ui.fragments.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    private val viewModel: RecipeDetailViewModel by viewModels()
    private val args: RecipeDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        binding.ivDetailFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        lifecycleScope.launch {
            viewModel.isLoadingState.collect {
                binding.progressBarDetail.visibility = if(it) View.VISIBLE else View.GONE
                binding.clDetail.visibility = if(it) View.GONE else View.VISIBLE
            }
        }

        lifecycleScope.launch {
            viewModel.recipeState.collect {
                it?.let {
                    binding.tvDetailTitle.text = it.name
                    binding.chipDetailCategory.text = it.category
                    binding.tvDetailInstructions.text = it.instructions
                    Glide.with(binding.root)
                        .load(it.thumb)
                        .into(binding.ivDetailImage)
                    binding.ivDetailFavorite.setImageResource(
                        if (it.isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                    )

                    binding.btnDetailVideo.setOnClickListener {
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loadRecipe(args.id)
        }

        return binding.root
    }
}