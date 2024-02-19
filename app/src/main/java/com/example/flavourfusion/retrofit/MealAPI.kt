package com.example.flavourfusion.retrofit

import com.example.flavourfusion.pojo.CategoryList
import com.example.flavourfusion.pojo.MealsByCategoryList
import com.example.flavourfusion.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String) : Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searhQuery:String) : Call<MealList>
}