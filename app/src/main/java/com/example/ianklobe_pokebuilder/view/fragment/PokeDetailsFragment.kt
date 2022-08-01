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
import com.example.ianklobe_pokebuilder.model.SinglePokeResponse
import com.example.ianklobe_pokebuilder.model.UIState
import java.util.*

class PokeDetailsFragment: ViewModelFragment() {
    lateinit var binding: FragmentPokeDetailsBinding
    val args: PokeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokeDetailsBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        configureObserver()

        return binding.root
    }

    private fun configureObserver() {
        viewModel.pokeDetails.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Loading -> {
                    viewModel.getSinglePokemon(args.pokemonChoice.name)
                }
                is UIState.Error -> {
                    binding.pbLoadingDetail.visibility = View.GONE
                    binding.tvLoadingTextDetail.text = state.error.message
                }
                is UIState.Success<*> -> {
                    binding.apply {
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

                        tvPokeNameDetail.text = pokemon.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
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
                        val weightCalc = getWeight(pokemon.weight)
                        tvHeightValue.text = resources.getString(R.string.height
                            ,heightCalc/12
                            ,heightCalc%12)
                        tvWeightValue.text = "Weight = ${String.format("%.2f",weightCalc)} lbs"
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