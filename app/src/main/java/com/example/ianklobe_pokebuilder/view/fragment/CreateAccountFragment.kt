package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.databinding.FragmentCreateAccountBinding
import com.example.ianklobe_pokebuilder.model.states.AccountStatus
import com.google.android.material.snackbar.Snackbar

class CreateAccountFragment: TrainerViewModelFragment() {
    private lateinit var binding: FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(layoutInflater)

        configureObservers()
        binding.apply {
            btnCreateAccount.setOnClickListener {
                validateInput()
            }
        }
        return binding.root
    }

    private fun validateInput() {

        when{
            !passwordCheck(binding.tietInputPassword.text.toString().trim(),
                binding.tietInputPasswordRepeat.text.toString().trim()) -> {
                Snackbar.make(binding.root,
                    "Passwords do not match",
                    Snackbar.LENGTH_SHORT)
                    .show()
            }
            !emailCheck(binding.tietInputEmail.text.toString().trim()) -> {
                Snackbar.make(binding.root,
                    "Invalid Email",
                    Snackbar.LENGTH_SHORT)
                    .show()
            }
            else -> {
                viewModel.createTrainer(
                    binding.tietInputEmail.text.toString(),
                    binding.tietInputPassword.text.toString())
            }
        }
    }

    private fun passwordCheck(password: String, retype: String): Boolean = password == retype

    private fun emailCheck(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun configureObservers() {
        viewModel.accountStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                AccountStatus.SUBMITTED -> {
                    findNavController().navigate(
                        CreateAccountFragmentDirections.actionNavCreateAccountToFilter()
                    )
                    Snackbar.make(binding.root,
                        "Welcome ${viewModel.currentTrainer.value?.email}!",
                        Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.signed()
                }
                AccountStatus.CREATION_ERROR -> Snackbar.make(binding.root,
                    "Account could not be created",
                    Snackbar.LENGTH_SHORT)
                    .show()
                AccountStatus.EMAIL_EXISTS -> Snackbar.make(binding.root,
                    "${binding.tietInputEmail.text.toString()} already exists.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                AccountStatus.EXISTS -> findNavController().popBackStack()
                else -> {}
            }
        }
    }
}