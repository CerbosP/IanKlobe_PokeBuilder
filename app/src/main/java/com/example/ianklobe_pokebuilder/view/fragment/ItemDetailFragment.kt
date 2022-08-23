package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ianklobe_pokebuilder.databinding.FragmentItemDetailBinding
import com.example.ianklobe_pokebuilder.model.response.ItemSpecific
import com.example.ianklobe_pokebuilder.model.response.PokeHeld
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.utils.formatName
import com.example.ianklobe_pokebuilder.view.adapter.ItemDetailAdapter

class ItemDetailFragment: PokeViewModelFragment() {
    private lateinit var binding: FragmentItemDetailBinding
    private val args: ItemDetailFragmentArgs by navArgs()

    private val itemDetailAdapter by lazy {
        ItemDetailAdapter(openDetails = ::openDetails)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemDetailBinding.inflate(layoutInflater)

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        configureObserver()

        return binding.root
    }

    private fun configureObserver() {
        viewModel.itemDetail.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Loading -> {
                    viewModel.getSingleItem(args.item.name)
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = state.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE

                        val item = (state.response as ItemSpecific)
                        tvItemName.text = item.name.formatName()
                        tvItemCost.text = "Cost: ${item.cost}"

                        Glide.with(ivItemSprite)
                            .load(item.sprites.default)
                            .into(ivItemSprite)

                        tvFlingPower.text = "Fling Power: ${item?.flingPower ?: "Cannot Fling"}"
                        tvFlingEffect.text = "Fling Effect: ${item.flingEffect?.name?.formatName() ?: "No Effect"}"

                        for(x in item.effect) {
                            if(x.language.name == "en") {
                                tvItemDescription.text = x.effect
                            }
                        }

                        if(item.heldBy.isNotEmpty()) {
                            itemDetailAdapter.setPokeList(item.heldBy)
                            rvItemPokemon.adapter = itemDetailAdapter
                        }
                    }
                }
            }
        }
    }

    private fun openDetails(heldBy: PokeHeld) {
        viewModel.setDetailLoadingState()
        findNavController().navigate(ItemDetailFragmentDirections
            .actionItemDetailToPokeDetail()
            .setPokemonChoice(heldBy.pokemon))
    }
}