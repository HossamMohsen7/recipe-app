package me.hossamohsen.recipeapp.ui.fragments.splash

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.data.state.UserState
import me.hossamohsen.recipeapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModels()
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        viewModel.checkUserLoggedIn {
            when (it) {
                is UserState.LoggedIn -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToRecipeActivity()
                    navController.navigate(action)
                }

                is UserState.NotLoggedIn -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    navController.navigate(action)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        binding.tvSplashVersion.text = "1.0.0"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}