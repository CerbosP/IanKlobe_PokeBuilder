package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentAbilityListBinding
import com.example.ianklobe_pokebuilder.model.response.*
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.view.adapter.AbilityAdapter

class AbilityListFragment : PokeViewModelFragment() {
    private lateinit var binding: FragmentAbilityListBinding

    private val abilityAdapter by lazy {
        AbilityAdapter(openDetails = ::openDetails)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAbilityListBinding.inflate(layoutInflater)

        configureObserver()

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun configureObserver() {
        viewModel.abilityList.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    viewModel.getAbility()
                }
                is UIState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvLoadingText.text = uiState.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE
                        abilityAdapter.setAbilityList((uiState.response as AbilityResponse).results)
                        root.setBackgroundColor(resources.getColor(R.color.logo_blue))
                        rvAbility.adapter = abilityAdapter
                    }
                }
            }
        }
    }

    private fun openDetails(abilityResponseData: AbilityResponseData) {
        viewModel.setAbilityDetailLoadingState()
        findNavController().navigate(AbilityListFragmentDirections
            .actionAbilityListToAbilityDetail(abilityResponseData))
    }
}