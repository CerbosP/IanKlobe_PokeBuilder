package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentMenuBinding
import com.example.ianklobe_pokebuilder.databinding.FragmentPokeDetailsBinding

class MenuFragment : PokeViewModelFragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnPokemonMenu.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections
                .actionNavMenuToFilter())
        }

        binding.btnAbilityMenu.setOnClickListener {
            viewModel.setAbilityLoadingState()
            findNavController().navigate(MenuFragmentDirections
                .actionNavMenuToAbilityList())
        }

        binding.btnItemMenu.setOnClickListener {
            viewModel.setItemCategoryLoadingState()
            findNavController().navigate(MenuFragmentDirections
                .actionNavMenuToCategoryList())
        }

        return binding.root
    }
}