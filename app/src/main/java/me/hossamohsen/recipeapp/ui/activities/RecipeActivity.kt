package me.hossamohsen.recipeapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.databinding.ActivityAuthBinding
import me.hossamohsen.recipeapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityRecipeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.recipeMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.recipe_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.topAppBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}