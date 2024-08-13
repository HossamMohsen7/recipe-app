package me.hossamohsen.recipeapp.ui.fragments.register

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
import me.hossamohsen.recipeapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.errorState.collect {
                _binding?.apply {
                    tvRegisterError.text = it
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        navController = findNavController()

        binding.tvAlreadyLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            navController.navigate(action)
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etRegisterFullName.editText!!.text.toString()
            val email = binding.etRegisterEmail.editText!!.text.toString()
            val password = binding.etRegisterPassword.editText!!.text.toString()

            binding.btnRegister.isEnabled = false
            viewModel.register(fullName, email, password) {
                if(it) {
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    navController.navigate(action)
                }
                binding.btnRegister.isEnabled = true
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}