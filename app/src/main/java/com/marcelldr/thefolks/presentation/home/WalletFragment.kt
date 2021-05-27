package com.marcelldr.thefolks.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcelldr.thefolks.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {
    private lateinit var binding: FragmentWalletBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        fun binding() {
        }

        binding()
    }
}