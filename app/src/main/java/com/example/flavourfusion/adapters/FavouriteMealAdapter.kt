package com.example.flavourfusion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flavourfusion.databinding.FragmentFavouritesBinding
import com.example.flavourfusion.databinding.MealItemBinding
import com.example.flavourfusion.pojo.Meal
import com.example.flavourfusion.pojo.MealsByCategory
import com.example.flavourfusion.viewModel.MealViewModel

class FavouriteMealAdapter : RecyclerView.Adapter<FavouriteMealAdapter.FavouritesMealViewModel>() {

    lateinit var onItemClick:((Meal) -> Unit)

    inner class FavouritesMealViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesMealViewModel {
        return FavouritesMealViewModel(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavouritesMealViewModel, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMeal.text= meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }
}