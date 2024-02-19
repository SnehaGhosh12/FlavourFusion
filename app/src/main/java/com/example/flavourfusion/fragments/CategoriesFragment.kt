package com.example.flavourfusion.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flavourfusion.activities.CategoryMealsActivity
import com.example.flavourfusion.activities.MainActivity
import com.example.flavourfusion.adapters.CategoryItemAdapter
import com.example.flavourfusion.databinding.FragmentCategoriesBinding
import com.example.flavourfusion.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesItemsAdapter: CategoryItemAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observeCategories()

        onCategoriesItemClick()
    }

    private fun onCategoriesItemClick() {
        categoriesItemsAdapter.onItemClick = {category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }

    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories->
        categoriesItemsAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesItemsAdapter = CategoryItemAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesItemsAdapter
        }

    }


}