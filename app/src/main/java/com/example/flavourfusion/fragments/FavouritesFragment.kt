package com.example.flavourfusion.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.flavourfusion.R
import com.example.flavourfusion.activities.MainActivity
import com.example.flavourfusion.activities.MealActivity
import com.example.flavourfusion.adapters.FavouriteMealAdapter
import com.example.flavourfusion.databinding.FragmentFavouritesBinding
import com.example.flavourfusion.pojo.Meal
import com.example.flavourfusion.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouritesMealAdapter: FavouriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavourites()
        onFavouriteItemClick()

        val itemTouchHelper= object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal: Meal = favouritesMealAdapter.differ.currentList[position]
                if (meal != null){
                        viewModel.deleteMeal(meal)

                    Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo",
                        View.OnClickListener {
                            viewModel.insertMeal(meal)
                        }
                    ).show()
                }
                else{
                    Toast.makeText(context,"Meal Not Found",Toast.LENGTH_SHORT).show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun onFavouriteItemClick() {
        favouritesMealAdapter.onItemClick = { meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        favouritesMealAdapter = FavouriteMealAdapter()
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favouritesMealAdapter
        }
    }

    private fun observeFavourites() {
        viewModel.observeFavouritesMealLiveData().observe(viewLifecycleOwner, Observer {meals->
            favouritesMealAdapter.differ.submitList(meals)
        })
    }

}