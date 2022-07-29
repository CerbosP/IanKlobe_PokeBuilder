package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ianklobe_pokebuilder.databinding.FragmentPokeListBinding
import com.example.ianklobe_pokebuilder.model.PokeResponse
import com.example.ianklobe_pokebuilder.model.UIState
import com.example.ianklobe_pokebuilder.utils.*
import com.example.ianklobe_pokebuilder.view.adapter.PokeAdapter

class PokeListFragment: ViewModelFragment() {
    lateinit var binding: FragmentPokeListBinding
    val args: PokeListFragmentArgs by navArgs()

    private val pokeAdapter by lazy {
        PokeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokeListBinding.inflate(layoutInflater)

        configureObserver(args.generationFilter)

        return binding.root
    }

    private fun configureObserver(gen: String) {
        viewModel.pokeList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    when(gen) {
                        "No filter" ->  {viewModel.getPokemon(1154, 0)}
                        "Gen 1 - Kanto" ->  {viewModel.getPokemon(GEN_ONE, 0)}
                        "Gen 2 - Johto" ->  {viewModel.getPokemon(GEN_TWO, GEN_TWO_OFFSET)}
                        "Gen 3 - Hoenn" ->  {viewModel.getPokemon(GEN_THREE, GEN_THREE_OFFSET)}
                        "Gen 4 - Sinnoh" ->  {viewModel.getPokemon(GEN_FOUR, GEN_FOUR_OFFSET)}
                        "Gen 5 - Unova" ->  {viewModel.getPokemon(GEN_FIVE, GEN_FIVE_OFFSET)}
                        "Gen 6 - Kalos" ->  {viewModel.getPokemon(GEN_SIX, GEN_SIX_OFFSET)}
                        "Gen 7 - Alola" ->  {viewModel.getPokemon(GEN_SEVEN_OFFSET, GEN_SEVEN_OFFSET)}
                        "Gen 8 - Galar" ->  {viewModel.getPokemon(GEN_EIGHT, GEN_EIGHT_OFFSET)}
                        "Alternate Forms" ->  {viewModel.getPokemon(FORM, FORM_OFFSET)}
                    }
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = uiState.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE
                        pokeAdapter.setPokeList((uiState.response as PokeResponse).results)
                        rvPokemon.adapter = pokeAdapter
                    }
                }
            }
        }
    }
}