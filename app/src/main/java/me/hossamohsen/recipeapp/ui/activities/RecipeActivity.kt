package me.hossamohsen.recipeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.data.local.SharedPreferencesManager
import me.hossamohsen.recipeapp.databinding.ActivityRecipeBinding
import me.hossamohsen.recipeapp.ui.fragments.home.HomeFragmentDirections


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

        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            if (destination.id == R.id.aboutFragment || destination.id == R.id.recipeDetailFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about_creator -> {
                val action = HomeFragmentDirections.actionHomeFragmentToAboutFragment()
                navController.navigate(action)
                true
            }

            R.id.menu_logout -> {
                //logout
                SharedPreferencesManager.saveString(SharedPreferencesManager.KEY_USER_ID, null)
                //go to auth activity
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}