package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.databinding.FragmentFilterBinding

class FilterFragment: ViewModelFragment() {
    lateinit var binding: FragmentFilterBinding
    var filterType: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)

        binding.swcFilterType.setOnClickListener {
            filterType = binding.swcFilterType.isChecked
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