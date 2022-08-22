package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentFilterBinding
import com.example.ianklobe_pokebuilder.model.response.PokeResponse
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.utils.TOTAL_MONS
import com.example.ianklobe_pokebuilder.utils.deformatName
import com.example.ianklobe_pokebuilder.utils.toPokeList
import com.google.android.material.snackbar.Snackbar


class FilterFragment: PokeViewModelFragment() {
    private lateinit var binding: FragmentFilterBinding
    private var filterType: Boolean = false
    private var gen: String = "All Pokémon"
    private var type: String = "Bug"
    private var egg: String = "indeterminate"
    private var typeNum: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        setFilters()

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.swcFilterType.setOnClickListener {
            filterType = binding.swcFilterType.isChecked

            binding.rgTypeOptions.visibility = if(filterType) { View.VISIBLE }
            else { View.GONE }

            if(filterType){
                binding.rbBug.isChecked
                type = "Bug"
                typeNum = 0
            }
        }

        binding.rgFilterOptions.setOnCheckedChangeListener { radioGroup, i ->
            changeFilters(i)
        }

        binding.rgGenOptions.setOnCheckedChangeListener { radioGroup, i ->
            changeGen(i)
        }

        binding.rgTypeOptions.setOnCheckedChangeListener { radioGroup, i ->
            changeType(i)
        }

        binding.rgEggOptions.setOnCheckedChangeListener { radioGroup, i ->
            changeEggGroup(i)
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

        configureObserver()

        binding.btnPokemonSearch.setOnClickListener {
            when(binding.rgFilterOptions.checkedRadioButtonId) {
                binding.rbNameFilter.id -> {
                    if(binding.actvNameFilter.text.isNotBlank()) {
                        viewModel.setDetailLoadingState()
                        findNavController().navigate(
                            FilterFragmentDirections
                                .actionFilterToPokeDetail()
                                .setPokemonName(
                                    binding.actvNameFilter.text.toString().trim().deformatName()
                                )
                        )
                    } else {
                        Snackbar.make(binding.root,
                            "Name filter cannot be empty or blank. Please use autofill to help find your pokemon.",
                            Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                binding.rbGenFilter.id -> {
                    if(filterType) {
                        viewModel.setTypeLoadingState()
                        findNavController().navigate(
                            FilterFragmentDirections
                                .actionFilterToPokeList()
                                .setFilterFilter(1)
                                .setGenerationFilter(gen)
                                .setTypeFilter(type.lowercase())
                                .setTypeNumber(typeNum)
                                .setShinyFilter(binding.swcShiny.isChecked)
                        )
                    } else {
                        viewModel.setPokeLoadingState()
                        findNavController().navigate(
                            FilterFragmentDirections
                                .actionFilterToPokeList()
                                .setFilterFilter(0)
                                .setGenerationFilter(gen)
                                .setShinyFilter(binding.swcShiny.isChecked)
                        )
                    }
                }
                binding.rbEggFilter.id -> {
                    viewModel.setEggLoadingState()
                    findNavController().navigate(
                        FilterFragmentDirections
                            .actionFilterToPokeList()
                            .setFilterFilter(2)
                            .setEggFilter(egg)
                            .setShinyFilter(binding.swcShiny.isChecked)
                    )
                }
            }
        }

        return binding.root
    }

    private fun configureObserver() {
        viewModel.getPokemon(limit = TOTAL_MONS, offset = 0)
        viewModel.pokeList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Success<*> -> {
                    binding.apply {
                        actvNameFilter.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.select_dialog_item,
                                (uiState.response as PokeResponse).toPokeList()
                            )
                        )
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        actvNameFilter.error = resources.getString(R.string.nope)
                    }
                }
                else -> {}
            }
        }
    }

    private fun setFilters() {
        binding.apply {
            actvNameFilter.visibility = View.GONE
            tilNameFilter.visibility = View.GONE
            rgEggOptions.visibility = View.GONE
            rgTypeOptions.visibility = if(filterType) { View.VISIBLE }
            else { View.GONE }
        }
    }

    private fun changeFilters(state: Int) {
        binding.apply {
            when (state) {
                rbGenFilter.id -> {
                    swcFilterType.isChecked = false
                    swcShiny.isChecked = false
                    rgGenOptions.visibility = View.VISIBLE
                    rbAll.isChecked
                    gen = "All Pokémon"
                    type = "Bug"
                    typeNum = 0
                    rbBug.isChecked
                    swcShiny.visibility = View.VISIBLE
                    swcFilterType.visibility = View.VISIBLE
                    rgEggOptions.visibility = View.GONE
                    actvNameFilter.visibility = View.GONE
                    tilNameFilter.visibility = View.GONE
                }
                rbNameFilter.id -> {
                    rgGenOptions.visibility = View.GONE
                    rgTypeOptions.visibility = View.GONE
                    swcShiny.visibility = View.GONE
                    swcFilterType.visibility = View.GONE
                    rgEggOptions.visibility = View.GONE
                    actvNameFilter.visibility = View.VISIBLE
                    tilNameFilter.visibility = View.VISIBLE
                    actvNameFilter.setText("")
                }
                rbEggFilter.id -> {
                    rgGenOptions.visibility = View.GONE
                    rgTypeOptions.visibility = View.GONE
                    swcShiny.visibility = View.GONE
                    swcFilterType.visibility = View.GONE
                    actvNameFilter.visibility = View.GONE
                    tilNameFilter.visibility = View.GONE
                    rgEggOptions.visibility = View.VISIBLE
                    egg = "indeterminate"
                    rbIndeterminateAmorphous.isChecked
                }
            }
        }
    }

    private fun changeGen(state: Int) {
        when (state) {
            binding.rbAll.id -> { gen = "All Pokémon" }
            binding.rbGenOne.id -> { gen = "Gen 1 - Kanto" }
            binding.rbGenTwo.id -> { gen = "Gen 2 - Johto" }
            binding.rbGenThree.id -> { gen = "Gen 3 - Hoenn" }
            binding.rbGenFour.id -> { gen = "Gen 4 - Sinnoh" }
            binding.rbGenFive.id -> { gen = "Gen 5 - Unova" }
            binding.rbGenSix.id -> { gen = "Gen 6 - Kalos" }
            binding.rbGenSeven.id -> { gen = "Gen 7 - Alola" }
            binding.rbGenEight.id -> { gen = "Gen 8 - Galar" }
            binding.rbGenNine.id -> { gen = "Alternate Forms" }
        }
    }

    private fun changeType(state: Int) {
        when (state) {
            binding.rbBug.id -> {
                type = "Bug"
                typeNum = 0
            }
            binding.rbDark.id -> {
                type = "Dark"
                typeNum = 1
            }
            binding.rbDragon.id -> {
                type = "Dragon"
                typeNum = 2
            }
            binding.rbElectric.id -> {
                type = "Electric"
                typeNum = 3
            }
            binding.rbFairy.id -> {
                type = "Fairy"
                typeNum = 4
            }
            binding.rbFighting.id -> {
                type = "Fighting"
                typeNum = 5
            }
            binding.rbFire.id -> {
                type = "Fire"
                typeNum = 6
            }
            binding.rbFlying.id -> {
                type = "Flying"
                typeNum = 7
            }
            binding.rbGhost.id -> {
                type = "Ghost"
                typeNum = 8
            }
            binding.rbGrass.id -> {
                type = "Grass"
                typeNum = 9
            }
            binding.rbGround.id -> {
                type = "Ground"
                typeNum = 10
            }
            binding.rbIce.id -> {
                type = "Ice"
                typeNum = 11
            }
            binding.rbNormal.id -> {
                type = "Normal"
                typeNum = 12
            }
            binding.rbPoison.id -> {
                type = "Poison"
                typeNum = 13
            }
            binding.rbPsychic.id -> {
                type = "Psychic"
                typeNum = 14
            }
            binding.rbRock.id -> {
                type = "Rock"
                typeNum = 15
            }
            binding.rbSteel.id -> {
                type = "Steel"
                typeNum = 16
            }
            binding.rbWater.id -> {
                type = "Water"
                typeNum = 17
            }
        }
    }

    private fun changeEggGroup(state: Int) {
        when(state) {
            binding.rbIndeterminateAmorphous.id -> { egg = "indeterminate" }
            binding.rbBugEgg.id -> { egg = "bug" }
            binding.rbDitto.id -> { egg = "ditto" }
            binding.rbDragonEgg.id -> { egg = "dragon" }
            binding.rbFairyEgg.id -> { egg = "fairy" }
            binding.rbFieldGround.id -> { egg = "ground" }
            binding.rbFlyingEgg.id -> { egg = "flying" }
            binding.rbGrassPlant.id -> { egg = "plant" }
            binding.rbHumanlike.id -> { egg = "humanshape" }
            binding.rbMineral.id -> { egg = "mineral" }
            binding.rbMonster.id -> { egg = "monster" }
            binding.rbWaterOne.id -> { egg = "water1" }
            binding.rbWaterTwo.id -> { egg = "water2" }
            binding.rbWaterThree.id -> { egg = "water3" }
            binding.rbNoEggs.id -> { egg = "no-eggs" }
        }
    }
}