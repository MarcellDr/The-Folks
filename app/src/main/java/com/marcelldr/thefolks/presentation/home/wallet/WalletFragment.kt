package com.marcelldr.thefolks.presentation.home.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            binding.walletPay.setOnClickListener {
                Toast.makeText(requireContext(), "NFC belum dinyalakan", Toast.LENGTH_SHORT).show()
            }
            binding.walletCheck.setOnClickListener {
                Toast.makeText(requireContext(), "NFC belum dinyalakan", Toast.LENGTH_SHORT).show()
            }
            binding.walletTopUp.setOnClickListener {
                Toast.makeText(requireContext(), "NFC belum dinyalakan", Toast.LENGTH_SHORT).show()
            }
            binding.walletHistory.setOnClickListener {
                Toast.makeText(requireContext(), "NFC belum dinyalakan", Toast.LENGTH_SHORT).show()
            }
        }

        binding()
    }
}