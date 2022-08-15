package com.example.ianklobe_pokebuilder.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ianklobe_pokebuilder.viewmodel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class PokeViewModelFragment: Fragment() {
    protected val viewModel: PokeViewModel by activityViewModels()
}