package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentAbilityDetailsBinding
import com.example.ianklobe_pokebuilder.model.response.AbilityPokemon
import com.example.ianklobe_pokebuilder.model.response.SpecificAbilityResponseData
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.utils.formatName
import com.example.ianklobe_pokebuilder.view.adapter.AbilityDetailAdapter

class AbilityDetailFragment : PokeViewModelFragment() {
    private lateinit var binding: FragmentAbilityDetailsBinding
    private val args: AbilityDetailFragmentArgs by navArgs()

    private val abilityDetailAdapter by lazy {
        AbilityDetailAdapter(openDetails = ::openDetails)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAbilityDetailsBinding.inflate(layoutInflater)

        configureObserver()

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun configureObserver() {
        viewModel.abilityDetail.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    viewModel.getSingleAbility(args.abilityDetail.name)
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = uiState.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE

                        val ability = (uiState.response as SpecificAbilityResponseData)

                        tvAbilityName.text = ability.name.formatName()

                        if(ability.effect.isNotEmpty()) {
                            for (x in ability.effect) {
                                if (x.language.name == "en") {
                                    tvAbilityDescription.text = x.effect
                                }
                            }
                        } else {
                            for (x in ability.flavor) {
                                if (x.language.name == "en") {
                                    tvAbilityDescription.text = x.flavor
                                }
                            }
                        }

                        abilityDetailAdapter.setPokeList(ability.pokemon)
                        rvAbilityPokemon.adapter = abilityDetailAdapter
                    }
                }
            }
        }
    }

    private fun openDetails(abilityPokemon: AbilityPokemon) {
        viewModel.setDetailLoadingState()
        findNavController().navigate(AbilityDetailFragmentDirections
            .actionAbilityDetailToPokeDetail()
            .setPokemonChoice(abilityPokemon.pokemon))
    }
}