package com.example.flavourfusion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flavourfusion.databinding.CategoryItemBinding
import com.example.flavourfusion.databinding.MealItemBinding
import com.example.flavourfusion.pojo.Category
import com.example.flavourfusion.pojo.Meal
import com.example.flavourfusion.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>(){

    lateinit var onItemClick:((MealsByCategory) -> Unit)

    private var mealsList = ArrayList<MealsByCategory>()

    inner class CategoryMealsViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    fun setMealsList(mealsList: List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        return CategoryMealsViewModel(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)

        holder.binding.tvMeal.text= mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

    }
}