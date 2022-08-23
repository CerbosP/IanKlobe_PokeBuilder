package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentItemCategoryListBinding
import com.example.ianklobe_pokebuilder.databinding.FragmentItemListBinding
import com.example.ianklobe_pokebuilder.model.response.ItemCategoryResponse
import com.example.ianklobe_pokebuilder.model.response.ItemCategoryResponseData
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.view.adapter.ItemCategoryAdapter

class ItemCategoryListFragment : PokeViewModelFragment() {
    private lateinit var binding: FragmentItemCategoryListBinding

    private val itemAdapter by lazy {
        ItemCategoryAdapter(openDetails = ::openDetails)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemCategoryListBinding.inflate(layoutInflater)

        configureObserver()

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun configureObserver() {
        viewModel.itemCategoryList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    viewModel.getItemCategory()
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = uiState.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE
                        itemAdapter.setItemList((uiState.response as ItemCategoryResponse).results)
                        root.setBackgroundColor(resources.getColor(R.color.logo_blue))
                        rvItemCategory.adapter = itemAdapter
                    }
                }
            }
        }
    }

    private fun openDetails(itemCategoryResponseData: ItemCategoryResponseData) {
        viewModel.setItemLoadingState()
        findNavController().navigate(ItemCategoryListFragmentDirections
            .actionCategoryListToItemList(itemCategoryResponseData))
    }
}