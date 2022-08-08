package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
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

            binding.spnTypes.visibility = if(filterType) { View.VISIBLE }
            else { View.GONE }
        }

        binding.rgFilterOptions.setOnCheckedChangeListener { radioGroup, i ->
            changeFilters(i)
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
                                (uiState.response as PokeResponse).toPokeList(requireContext())
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
            spnTypes.visibility = View.GONE
            actvNameFilter.visibility = View.GONE
            tilNameFilter.visibility = View.GONE
            spnTypes.visibility = if(filterType) { View.VISIBLE }
            else { View.GONE }
        }
    }

    private fun changeFilters(state: Int) {
        binding.apply {
            when (state) {
                rbGenFilter.id -> {
                    swcFilterType.isChecked = false
                    swcShiny.isChecked = false
                    spnGens.visibility = View.VISIBLE
                    spnGens.setSelection(0)
                    spnTypes.setSelection(0)
                    swcShiny.visibility = View.VISIBLE
                    swcFilterType.visibility = View.VISIBLE
                    actvNameFilter.visibility = View.GONE
                    tilNameFilter.visibility = View.GONE
                    pbNameFilter.visibility = View.GONE
                }
                rbNameFilter.id -> {
                    spnTypes.visibility = View.GONE
                    spnGens.visibility = View.GONE
                    swcShiny.visibility = View.GONE
                    swcFilterType.visibility = View.GONE
                    actvNameFilter.visibility = View.VISIBLE
                    tilNameFilter.visibility = View.VISIBLE
                    pbNameFilter.visibility = View.GONE
                    actvNameFilter.setText("")
                }
            }
        }
    }
}