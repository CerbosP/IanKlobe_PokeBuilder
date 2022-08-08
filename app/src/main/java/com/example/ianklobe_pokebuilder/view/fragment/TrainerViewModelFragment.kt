package com.example.ianklobe_pokebuilder.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ianklobe_pokebuilder.viewmodel.TrainerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class TrainerViewModelFragment: Fragment() {
    protected val viewModel: TrainerViewModel by activityViewModels()
}