package com.example.ianklobe_pokebuilder.view.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.FragmentLogInBinding
import com.example.ianklobe_pokebuilder.model.states.AccountStatus
import com.google.android.material.snackbar.Snackbar

class LogInFragment: TrainerViewModelFragment() {
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater)

        configureObservers()
        createAccountLink()

        binding.btnLogIn.setOnClickListener {
            if(binding.tietInputEmail.text!!.isNotBlank()
                and binding.tietInputPassword.text!!.isNotBlank())
            viewModel.signIn(binding.tietInputEmail.text.toString().trim(),
                binding.tietInputPassword.text.toString().trim())
            else {
                if(binding.tietInputEmail.text!!.isBlank())
                    binding.tietInputEmail.error = "Email cannot be blank!"
                if(binding.tietInputPassword.text!!.isBlank())
                    binding.tietInputPassword.error = "Email cannot be blank!"
            }
        }

        binding.btnDebug.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections
                .actionLogInToFilter())
        }

        return binding.root
    }

    private fun createAccountLink() {
        val ss = SpannableString(resources.getString(R.string.account))
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ResourcesCompat.getColor(resources, R.color.purple_200, null)
                ds.isUnderlineText = true
            }
            override fun onClick(p0: View) {
                binding.root.findNavController().navigate(
                    LogInFragmentDirections.actionLogInToCreateAccount()
                )
            }
        }
        ss.setSpan(clickableSpan, 0, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvCreateAccount.apply {
            text = ss
            movementMethod = LinkMovementMethod()
        }
    }

    private fun configureObservers() {
        viewModel.accountStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                AccountStatus.SIGN_IN_ERROR -> {
                    Snackbar.make(binding.root,
                        "Could not sign in, please try again.",
                        Snackbar.LENGTH_SHORT)
                        .show()
                }
                AccountStatus.SIGNED_IN -> {
                    moveToFilters()
                    Snackbar.make(binding.root,
                        "Welcome ${viewModel.currentTrainer.value?.email ?: ""}!",
                        Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.signed()
                }
                else -> {}
            }
        }
    }

    private fun moveToFilters() {
        findNavController().navigate(LogInFragmentDirections
            .actionLogInToFilter())
    }

    override fun onResume() {
        if(viewModel.currentTrainer.value != null) {
            Snackbar.make(binding.root,
                "Goodbye ${viewModel.currentTrainer.value?.email ?: ""}!",
                Snackbar.LENGTH_SHORT)
                .show()
            viewModel.logout()
        }
        super.onResume()
    }
}

