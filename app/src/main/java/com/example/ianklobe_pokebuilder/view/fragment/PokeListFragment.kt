package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ianklobe_pokebuilder.databinding.FragmentPokeListBinding
import com.example.ianklobe_pokebuilder.model.PokeResponse
import com.example.ianklobe_pokebuilder.model.TypeResponse
import com.example.ianklobe_pokebuilder.model.UIState
import com.example.ianklobe_pokebuilder.utils.*
import com.example.ianklobe_pokebuilder.view.adapter.PokeAdapter
import com.example.ianklobe_pokebuilder.view.adapter.TypeAdapter

class PokeListFragment : ViewModelFragment() {
    lateinit var binding: FragmentPokeListBinding
    val args: PokeListFragmentArgs by navArgs()

    private val pokeAdapter by lazy {
        PokeAdapter()
    }

    private val typeAdapter by lazy {
        TypeAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokeListBinding.inflate(layoutInflater)

        configureObserver(args.filterFilter)

        return binding.root
    }

    private fun configureObserver(filter: Boolean) {
        if (filter) {
            viewModel.pokeTypeList.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is UIState.Loading -> {
                        viewModel.getPokemonByType(args.typeFilter)
                    }
                    is UIState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.tvLoadingText.text = uiState.error.message
                    }
                    is UIState.Success<*> -> {
                        binding.apply {
                            pbLoading.visibility = View.GONE
                            tvLoadingText.visibility = View.GONE
                            typeAdapter.setShiny(args.shinyFilter)
                            when (args.generationFilter) {
                                "No filter" -> {
                                    typeAdapter.setGenStart(0)
                                    typeAdapter.setGenEnd(10250)
                                }
                                "Gen 1 - Kanto" -> {
                                    typeAdapter.setGenStart(0)
                                    typeAdapter.setGenEnd(GEN_TWO_OFFSET+1)
                                }
                                "Gen 2 - Johto" -> {
                                    typeAdapter.setGenStart(GEN_TWO_OFFSET)
                                    typeAdapter.setGenEnd(GEN_THREE_OFFSET+1)
                                }
                                "Gen 3 - Hoenn" -> {
                                    typeAdapter.setGenStart(GEN_THREE_OFFSET)
                                    typeAdapter.setGenEnd(GEN_FOUR_OFFSET+1)
                                }
                                "Gen 4 - Sinnoh" -> {
                                    typeAdapter.setGenStart(GEN_FOUR_OFFSET)
                                    typeAdapter.setGenEnd(GEN_FIVE_OFFSET+1)
                                }
                                "Gen 5 - Unova" -> {
                                    typeAdapter.setGenStart(GEN_FIVE_OFFSET)
                                    typeAdapter.setGenEnd(GEN_SIX_OFFSET+1)
                                }
                                "Gen 6 - Kalos" -> {
                                    typeAdapter.setGenStart(GEN_SIX_OFFSET)
                                    typeAdapter.setGenEnd(GEN_SEVEN_OFFSET+1)
                                }
                                "Gen 7 - Alola" -> {
                                    typeAdapter.setGenStart(GEN_SEVEN_OFFSET)
                                    typeAdapter.setGenEnd(GEN_EIGHT_OFFSET+1)
                                }
                                "Gen 8 - Galar" -> {
                                    typeAdapter.setGenStart(GEN_EIGHT_OFFSET)
                                    typeAdapter.setGenEnd(FORM_OFFSET+1)
                                }
                                "Alternate Forms" -> {
                                    typeAdapter.setGenStart(10000)
                                    typeAdapter.setGenEnd(10250)
                                }
                            }
                            typeAdapter.setTypeList((uiState.response as TypeResponse).pokemon)
                            rvPokemon.adapter = typeAdapter

                        }
                    }
                }

            }

        } else {
            viewModel.pokeList.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is UIState.Loading -> {
                        when (args.generationFilter) {
                            "No filter" -> { viewModel.getPokemon(1154, 0) }
                            "Gen 1 - Kanto" -> { viewModel.getPokemon(GEN_ONE, 0) }
                            "Gen 2 - Johto" -> { viewModel.getPokemon(GEN_TWO, GEN_TWO_OFFSET) }
                            "Gen 3 - Hoenn" -> { viewModel.getPokemon(GEN_THREE, GEN_THREE_OFFSET) }
                            "Gen 4 - Sinnoh" -> { viewModel.getPokemon(GEN_FOUR, GEN_FOUR_OFFSET) }
                            "Gen 5 - Unova" -> { viewModel.getPokemon(GEN_FIVE, GEN_FIVE_OFFSET) }
                            "Gen 6 - Kalos" -> { viewModel.getPokemon(GEN_SIX, GEN_SIX_OFFSET) }
                            "Gen 7 - Alola" -> { viewModel.getPokemon(GEN_SEVEN, GEN_SEVEN_OFFSET) }
                            "Gen 8 - Galar" -> { viewModel.getPokemon(GEN_EIGHT, GEN_EIGHT_OFFSET) }
                            "Alternate Forms" -> { viewModel.getPokemon(FORM, FORM_OFFSET) }
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
                            pokeAdapter.setShiny(args.shinyFilter)
                            rvPokemon.adapter = pokeAdapter
                        }
                    }
                }
            }
        }
    }
}