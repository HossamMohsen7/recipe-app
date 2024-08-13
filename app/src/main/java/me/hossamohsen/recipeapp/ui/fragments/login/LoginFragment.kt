package me.hossamohsen.recipeapp.ui.fragments.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.databinding.FragmentLoginBinding
import me.hossamohsen.recipeapp.databinding.FragmentSplashBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.errorState.collect {
                _binding?.apply {
                    tvLoginError.text = it
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.tvRegisterNow.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navController.navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.editText!!.text.toString()
            val password = binding.etLoginPassword.editText!!.text.toString()
            binding.btnLogin.isEnabled = false
            viewModel.login(email, password) {
                if(it) {
                    val action = LoginFragmentDirections.actionLoginFragmentToRecipeActivity()
                    navController.navigate(action)
                }
                binding.btnLogin.isEnabled = true
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}