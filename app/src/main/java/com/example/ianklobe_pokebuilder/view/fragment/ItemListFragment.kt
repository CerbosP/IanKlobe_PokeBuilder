package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentItemListBinding
import com.example.ianklobe_pokebuilder.model.response.ItemResponse
import com.example.ianklobe_pokebuilder.model.response.ItemResponseData
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.view.adapter.ItemAdapter

class ItemListFragment : PokeViewModelFragment() {
    private lateinit var binding: FragmentItemListBinding
    private val args: ItemListFragmentArgs by navArgs()

    private val itemAdapter by lazy {
        ItemAdapter(openDetails = ::openDetails)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemListBinding.inflate(layoutInflater)

        configureObserver()

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun configureObserver() {
        viewModel.itemList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    viewModel.getItems(args.itemCategory.name)
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = uiState.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE
                        itemAdapter.setItemList((uiState.response as ItemResponse).items)
                        root.setBackgroundColor(resources.getColor(R.color.logo_blue))
                        rvItems.adapter = itemAdapter
                    }
                }
            }
        }
    }

    private fun openDetails(itemResponseData: ItemResponseData) {
        viewModel.setItemDetailLoadingState()
        findNavController().navigate(ItemListFragmentDirections
            .actionItemListToItemDetail(itemResponseData))
    }
}