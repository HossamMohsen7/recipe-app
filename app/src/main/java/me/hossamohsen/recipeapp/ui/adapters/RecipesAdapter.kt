package me.hossamohsen.recipeapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.hossamohsen.recipeapp.R
import me.hossamohsen.recipeapp.data.models.Meal
import me.hossamohsen.recipeapp.databinding.ItemRecipeBinding

class RecipesAdapter(private var recipes: List<Meal>, private val onItemClick: (item: Meal) -> Unit, private val onFavoriteClick: (item: Meal) -> Unit) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {
    class ViewHolder(private val itemBinding: ItemRecipeBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(recipe: Meal, onItemClick: (item: Meal) -> Unit, onFavoriteClick: (item: Meal) -> Unit) {
            Log.i("RecipesAdapter", "bind: $recipe")

            itemBinding.tvRecipeName.text = recipe.name
            itemBinding.tvRecipeCategory.text = recipe.category
            Glide.with(itemBinding.root)
                .load(recipe.thumb)
                .into(itemBinding.tvRecipeImage)

            if(recipe.isFavorite) {
                itemBinding.ivRecipeFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                itemBinding.ivRecipeFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            itemBinding.ivRecipeFavorite.setOnClickListener {
                onFavoriteClick(recipe)
            }

            itemBinding.root.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    fun setRecipes(recipes: List<Meal>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, onItemClick, onFavoriteClick)

    }
}