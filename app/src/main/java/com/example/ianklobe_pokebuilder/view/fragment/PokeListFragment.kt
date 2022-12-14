package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentPokeListBinding
import com.example.ianklobe_pokebuilder.model.response.EggResponse
import com.example.ianklobe_pokebuilder.model.response.PokeResponse
import com.example.ianklobe_pokebuilder.model.response.PokeResponseData
import com.example.ianklobe_pokebuilder.model.response.TypeResponse
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.utils.*
import com.example.ianklobe_pokebuilder.view.adapter.PokeAdapter
import com.example.ianklobe_pokebuilder.view.adapter.TypeAdapter

class PokeListFragment : PokeViewModelFragment() {
    private lateinit var binding: FragmentPokeListBinding
    private val args: PokeListFragmentArgs by navArgs()

    private val pokeAdapter by lazy {
        PokeAdapter(openDetails = ::openDetails)
    }

    private val typeAdapter by lazy {
        TypeAdapter(openDetails = ::openDetails)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokeListBinding.inflate(layoutInflater)

        configureObserver(args.filterFilter)

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun configureObserver(filter: Int) {
        when (filter) {
            0 -> {
                viewModel.pokeList.observe(viewLifecycleOwner) { uiState ->
                    when (uiState) {
                        is UIState.Loading -> {
                            when (args.generationFilter) {
                                "All Pokémon" -> {
                                    viewModel.getPokemon(TOTAL_MONS, 0)
                                }
                                "Gen 1 - Kanto" -> {
                                    viewModel.getPokemon(GEN_ONE, 0)
                                }
                                "Gen 2 - Johto" -> {
                                    viewModel.getPokemon(GEN_TWO, GEN_TWO_OFFSET)
                                }
                                "Gen 3 - Hoenn" -> {
                                    viewModel.getPokemon(GEN_THREE, GEN_THREE_OFFSET)
                                }
                                "Gen 4 - Sinnoh" -> {
                                    viewModel.getPokemon(GEN_FOUR, GEN_FOUR_OFFSET)
                                }
                                "Gen 5 - Unova" -> {
                                    viewModel.getPokemon(GEN_FIVE, GEN_FIVE_OFFSET)
                                }
                                "Gen 6 - Kalos" -> {
                                    viewModel.getPokemon(GEN_SIX, GEN_SIX_OFFSET)
                                }
                                "Gen 7 - Alola" -> {
                                    viewModel.getPokemon(GEN_SEVEN, GEN_SEVEN_OFFSET)
                                }
                                "Gen 8 - Galar" -> {
                                    viewModel.getPokemon(GEN_EIGHT, GEN_EIGHT_OFFSET)
                                }
                                "Alternate Forms" -> {
                                    viewModel.getPokemon(FORM, FORM_OFFSET)
                                }
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
                                root.setBackgroundColor(resources.getColor(R.color.logo_blue))
                                rvPokemon.adapter = pokeAdapter
                            }
                        }
                    }
                }
            }
            1 -> {
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
                                    "All Pokémon" -> {
                                        typeAdapter.setGenStart(0)
                                        typeAdapter.setGenEnd(FORMS_END)
                                    }
                                    "Gen 1 - Kanto" -> {
                                        typeAdapter.setGenStart(0)
                                        typeAdapter.setGenEnd(GEN_TWO_OFFSET + 1)
                                    }
                                    "Gen 2 - Johto" -> {
                                        typeAdapter.setGenStart(GEN_TWO_OFFSET)
                                        typeAdapter.setGenEnd(GEN_THREE_OFFSET + 1)
                                    }
                                    "Gen 3 - Hoenn" -> {
                                        typeAdapter.setGenStart(GEN_THREE_OFFSET)
                                        typeAdapter.setGenEnd(GEN_FOUR_OFFSET + 1)
                                    }
                                    "Gen 4 - Sinnoh" -> {
                                        typeAdapter.setGenStart(GEN_FOUR_OFFSET)
                                        typeAdapter.setGenEnd(GEN_FIVE_OFFSET + 1)
                                    }
                                    "Gen 5 - Unova" -> {
                                        typeAdapter.setGenStart(GEN_FIVE_OFFSET)
                                        typeAdapter.setGenEnd(GEN_SIX_OFFSET + 1)
                                    }
                                    "Gen 6 - Kalos" -> {
                                        typeAdapter.setGenStart(GEN_SIX_OFFSET)
                                        typeAdapter.setGenEnd(GEN_SEVEN_OFFSET + 1)
                                    }
                                    "Gen 7 - Alola" -> {
                                        typeAdapter.setGenStart(GEN_SEVEN_OFFSET)
                                        typeAdapter.setGenEnd(GEN_EIGHT_OFFSET + 1)
                                    }
                                    "Gen 8 - Galar" -> {
                                        typeAdapter.setGenStart(GEN_EIGHT_OFFSET)
                                        typeAdapter.setGenEnd(FORM_OFFSET + 1)
                                    }
                                    "Alternate Forms" -> {
                                        typeAdapter.setGenStart(FORMS_START)
                                        typeAdapter.setGenEnd(FORMS_END)
                                    }
                                }
                                typeAdapter.setTypeList((uiState.response as TypeResponse).pokemon)
                                rvPokemon.adapter = typeAdapter
                                val color = binding.root.resources.getIntArray(R.array.types_codes)
                                binding.root.setBackgroundColor(color[args.typeNumber])
                            }
                        }
                    }
                }
            }
            2 -> {
                viewModel.pokeEgg.observe(viewLifecycleOwner) { uiState ->
                    when (uiState) {
                        is UIState.Loading -> {
                            viewModel.getEggGroup(args.eggFilter)
                        }
                        is UIState.Error -> {
                            binding.pbLoading.visibility = View.GONE
                            binding.tvLoadingText.text = uiState.error.message
                        }
                        is UIState.Success<*> -> {
                            binding.apply {
                                pbLoading.visibility = View.GONE
                                tvLoadingText.visibility = View.GONE
                                pokeAdapter.setPokeList((uiState.response as EggResponse).pokemonSpecies)
                                pokeAdapter.setShiny(args.shinyFilter)
                                root.setBackgroundColor(resources.getColor(R.color.logo_blue))
                                rvPokemon.adapter = pokeAdapter
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openDetails(pokeResponseData: PokeResponseData) {
        viewModel.setDetailLoadingState()
        findNavController().navigate(PokeListFragmentDirections.actionPokeListToPokeDetail()
            .setPokemonChoice(pokeResponseData))
    }
}