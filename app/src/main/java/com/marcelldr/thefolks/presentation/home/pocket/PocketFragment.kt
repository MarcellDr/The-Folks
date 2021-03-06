package com.marcelldr.thefolks.presentation.home.pocket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcelldr.thefolks.databinding.FragmentPocketBinding
import com.thefinestartist.finestwebview.FinestWebView


class PocketFragment : Fragment() {
    private lateinit var binding: FragmentPocketBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPocketBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        fun binding() {
        }

        binding()
    }
}