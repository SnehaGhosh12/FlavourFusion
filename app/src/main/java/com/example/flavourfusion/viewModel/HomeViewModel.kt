package com.example.flavourfusion.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavourfusion.db.MealDatabase
import com.example.flavourfusion.pojo.Category
import com.example.flavourfusion.pojo.CategoryList
import com.example.flavourfusion.pojo.MealsByCategoryList
import com.example.flavourfusion.pojo.MealsByCategory
import com.example.flavourfusion.pojo.Meal
import com.example.flavourfusion.pojo.MealList
import com.example.flavourfusion.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
):ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favouritesLiveData = mealDatabase.mealDao().getAllMeals()
    private val searchedMealLiveData = MutableLiveData<List<Meal>>()

    init {
        getRandomMeal()
    }

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>?, response: Response<MealList>?) {

                if(response?.body() !=null){
                    val randomMeal : Meal =response.body()!!.meals[0]
                    randomMealLiveData.value= randomMeal

                }else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>?, t: Throwable?) {
                Log.d("HomeFragment", t?.message.toString())
                
            }
        })

    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>?, response: Response<MealsByCategoryList>?) {
                if (response?.body() != null){
                    popularItemsLiveData.value=response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>?, t: Throwable?) {
                Log.d("HomeFragment",t?.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>?, response: Response<CategoryList>?) {
                response?.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>?, t: Throwable?) {
                Log.d("HomeFragment",t?.message.toString())
            }
        })
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun searchMeals(searchQuery:String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>?, response: Response<MealList>?) {
                val mealList = response?.body()?.meals
                mealList?.let {
                    searchedMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>?, t: Throwable?) {
                Log.d("HomeFragment", t?.message.toString())
            }
        }
    )

    fun observeSearchedMealsLiveData():LiveData<List<Meal>> = searchedMealLiveData

    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavouritesMealLiveData():LiveData<List<Meal>>{
        return favouritesLiveData
    }
}