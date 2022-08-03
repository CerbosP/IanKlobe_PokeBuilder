package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.databinding.FragmentFilterBinding
import com.google.android.material.snackbar.Snackbar




class FilterFragment: ViewModelFragment() {
    private lateinit var binding: FragmentFilterBinding
    private var filterType: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        binding.spnTypes.visibility = if(filterType) { View.VISIBLE }
        else { View.GONE }

        binding.swcFilterType.setOnClickListener {
            filterType = binding.swcFilterType.isChecked

            binding.spnTypes.visibility = if(filterType) { View.VISIBLE }
            else { View.GONE }
        }

        binding.swcShiny.setOnClickListener {
            if(binding.swcShiny.isChecked)
            Snackbar.make(binding.root,
                    "Warning! Some alternate forms shiny sprites may not load.",
                    Snackbar.LENGTH_LONG)
                .setAction("UNDO", View.OnClickListener {
                    binding.swcShiny.isChecked = false
                })
                .show()
        }


        binding.btnPokemonSearch.setOnClickListener {
            if(filterType) {
                viewModel.setTypeLoadingState()
                findNavController().navigate(
                    FilterFragmentDirections
                        .actionFilterToPokeList()
                        .setFilterFilter(filterType)
                        .setGenerationFilter(binding.spnGens.selectedItem.toString())
                        .setTypeFilter(binding.spnTypes.selectedItem.toString().lowercase())
                        .setTypeNumber(binding.spnTypes.selectedItemPosition)
                        .setShinyFilter(binding.swcShiny.isChecked)
                )
            } else {
                viewModel.setPokeLoadingState()
                findNavController().navigate(
                    FilterFragmentDirections
                        .actionFilterToPokeList()
                        .setFilterFilter(filterType)
                        .setGenerationFilter(binding.spnGens.selectedItem.toString())
                        .setShinyFilter(binding.swcShiny.isChecked)
                )
            }
        }

        return binding.root
    }
}