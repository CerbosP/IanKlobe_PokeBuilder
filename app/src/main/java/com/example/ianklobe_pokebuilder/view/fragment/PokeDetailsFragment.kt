package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentPokeDetailsBinding
import com.example.ianklobe_pokebuilder.model.response.SinglePokeResponse
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.utils.formatName

class PokeDetailsFragment: ViewModelFragment() {
    private lateinit var binding: FragmentPokeDetailsBinding
    private val args: PokeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokeDetailsBinding.inflate(layoutInflater)

        binding.ibtnBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        configureObserver()

        return binding.root
    }

    private fun configureObserver() {
        viewModel.pokeDetails.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Loading -> {
                    viewModel.getSinglePokemon(args.pokemonChoice?.name ?: args.pokemonName)
                }
                is UIState.Error -> {
                    binding.pbLoadingDetail.visibility = View.GONE
                    binding.tvLoadingTextDetail.text = state.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        progressCircularAtk.visibility = View.VISIBLE
                        progressCircularHp.visibility = View.VISIBLE
                        progressCircularDef.visibility = View.VISIBLE
                        progressCircularSpd.visibility = View.VISIBLE
                        progressCircularSpe.visibility = View.VISIBLE
                        progressCircularSpa.visibility = View.VISIBLE

                        tvSpaStat.visibility = View.VISIBLE
                        tvAtkStat.visibility = View.VISIBLE
                        tvSpeStat.visibility = View.VISIBLE
                        tvDefStat.visibility = View.VISIBLE
                        tvSpdStat.visibility = View.VISIBLE
                        tvHpStat.visibility = View.VISIBLE

                        pbLoadingDetail.visibility = View.GONE
                        tvLoadingTextDetail.visibility = View.GONE
                        val pokemon = (state.response as SinglePokeResponse)

                        Glide.with(ivPokeSpriteDetail)
                            .load(pokemon.sprites.frontDefault)
                            .placeholder(R.drawable.ball_loading)
                            .thumbnail(Glide.with(ivPokeSpriteDetail).load(R.drawable.ball_loading))
                            .dontAnimate()
                            .into(ivPokeSpriteDetail)

                        Glide.with(ivShinySpriteDetail)
                            .load(pokemon.sprites.frontShiny)
                            .placeholder(R.drawable.ball_loading)
                            .thumbnail(Glide.with(ivShinySpriteDetail).load(R.drawable.ball_loading))
                            .dontAnimate()
                            .into(ivShinySpriteDetail)

                        tvPokeNameDetail.text = pokemon.name.formatName()

                        val color = binding.root.resources.getIntArray(R.array.types_codes)
                        val typeList = binding.root.resources.getStringArray(R.array.types)

                        tvTypeOneDetail.text = pokemon.types[0].type.name.formatName()

                        tvTypeOneDetail.setBackgroundColor(
                            color[typeList.indexOf(tvTypeOneDetail.text.toString())])

                        if(pokemon.types.size > 1) {
                            tvTypeTwoDetail.visibility = View.VISIBLE
                            tvTypeTwoDetail.text = pokemon.types[1].type.name.formatName()

                            tvTypeTwoDetail.setBackgroundColor(
                                color[typeList.indexOf(tvTypeTwoDetail.text.toString())])
                        }



                        tvAbilityOneDetail.text = pokemon.abilities[0].ability.name.formatName()

                        when (pokemon.abilities.size) {
                            1 -> {
                                tvAbilityTwoDetail.visibility = View.GONE
                                tvAbilityThreeDetail.visibility = View.GONE
                            }
                            2 -> {
                                if(pokemon.abilities[1].hiddenAbility) {
                                    tvAbilityThreeDetail.text = pokemon.abilities[1].ability.name.formatName()
                                    tvAbilityTwoDetail.visibility = View.GONE
                                } else {
                                    tvAbilityTwoDetail.text = pokemon.abilities[1].ability.name.formatName()
                                    tvAbilityThreeDetail.visibility = View.GONE
                                }
                            }
                            3 -> {
                                tvAbilityTwoDetail.text = pokemon.abilities[1].ability.name.formatName()
                                tvAbilityThreeDetail.text = pokemon.abilities[2].ability.name.formatName()
                            }
                        }

                        progressCircularHp.progress = pokemon.stats[0].baseStat
                        progressCircularAtk.progress = pokemon.stats[1].baseStat
                        progressCircularDef.progress = pokemon.stats[2].baseStat
                        progressCircularSpa.progress = pokemon.stats[3].baseStat
                        progressCircularSpd.progress = pokemon.stats[4].baseStat
                        progressCircularSpe.progress = pokemon.stats[5].baseStat

                        tvHpStatValue.text = pokemon.stats[0].baseStat.toString()
                        tvAtkStatValue.text = pokemon.stats[1].baseStat.toString()
                        tvDefStatValue.text = pokemon.stats[2].baseStat.toString()
                        tvSpaStatValue.text = pokemon.stats[3].baseStat.toString()
                        tvSpdStatValue.text = pokemon.stats[4].baseStat.toString()
                        tvSpeStatValue.text = pokemon.stats[5].baseStat.toString()

                        val heightCalc = getHeight(pokemon.height)
                        val weightCalc = String.format("%.2f",getWeight(pokemon.weight))

                        tvHeightValue.text = resources.getString(R.string.height
                            ,heightCalc/12
                            ,heightCalc%12)
                        tvWeightValue.text = resources.getString(R.string.weight
                            ,weightCalc)
                    }
                }
            }
        }
    }

    private fun getHeight(height: Int): Int {
        var newHeight: Double = height.toDouble()
        newHeight /= 10
        newHeight *= 39.37
        newHeight += 0.5
        return newHeight.toInt()
    }

    private fun getWeight(weight: Int): Double {
        var newWeight: Double = weight.toDouble()
        newWeight /= 10
        newWeight *= 2.205
        return newWeight
    }
}