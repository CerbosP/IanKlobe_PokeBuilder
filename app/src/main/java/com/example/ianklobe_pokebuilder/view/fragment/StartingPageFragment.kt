package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.databinding.FragmentStartingPageBinding

class StartingPageFragment: ViewModelFragment() {
    private lateinit var binding: FragmentStartingPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartingPageBinding.inflate(layoutInflater)

        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(StartingPageFragmentDirections
                .actionStartingPageToFilter())
        }

        return binding.root
    }
}