package com.example.ianklobe_pokebuilder.view.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}