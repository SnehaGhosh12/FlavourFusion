package com.example.flavourfusion.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.flavourfusion.R
import com.example.flavourfusion.activities.CategoryMealsActivity
import com.example.flavourfusion.activities.MainActivity
import com.example.flavourfusion.activities.MealActivity
import com.example.flavourfusion.adapters.CategoryItemAdapter
import com.example.flavourfusion.adapters.PopularItemAdapter
import com.example.flavourfusion.databinding.FragmentHomeBinding
import com.example.flavourfusion.pojo.MealsByCategory
import com.example.flavourfusion.pojo.Meal
import com.example.flavourfusion.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:PopularItemAdapter
    private lateinit var categoriesItemsAdapter:CategoryItemAdapter


    companion object{
        const val MEAL_ID= "com.example.flavourfusion.fragments.idMeal"
        const val MEAL_NAME= "com.example.flavourfusion.fragments.nameMeal"
        const val MEAL_THUMB= "com.example.flavourfusion.fragments.thumbMeal"
        const val CATEGORY_NAME= "com.example.flavourfusion.fragments.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
//        homeMvvm= ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter = PopularItemAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




//        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        preparePopularItemsRecyclerView()
        viewModel.getPopularItems()
        ovservePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesItemsRecyclerView()
        viewModel.getCategories()
        ovserveCategoriesLiveData()
        onCategoriesItemClick()

        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun prepareCategoriesItemsRecyclerView() {
        categoriesItemsAdapter = CategoryItemAdapter()
        binding.recviewCategories.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter= categoriesItemsAdapter
        }
    }

    private fun onCategoriesItemClick() {
        categoriesItemsAdapter.onItemClick = {category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }

    }

    private fun ovserveCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesItemsAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recviewPopularMeal.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun ovservePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList->
                popularItemsAdapter.setMeals(mealList= mealList as ArrayList<MealsByCategory>)

            })
    }

    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLivedata().observe(viewLifecycleOwner,
            {meal->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        })
    }

}