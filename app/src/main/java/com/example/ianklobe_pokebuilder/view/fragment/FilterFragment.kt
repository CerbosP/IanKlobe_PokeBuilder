package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.databinding.FragmentFilterBinding

class FilterFragment: ViewModelFragment() {
    lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)

        binding.btnPokemonSearch.setOnClickListener {
            viewModel.setLoadingState()

            findNavController().navigate(FilterFragmentDirections
                .actionFilterToPokeList().setGenerationFilter(binding.spnGens.selectedItem.toString()))
        }

        return binding.root
    }
}