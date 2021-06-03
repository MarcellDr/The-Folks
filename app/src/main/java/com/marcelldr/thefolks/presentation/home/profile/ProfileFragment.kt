package com.marcelldr.thefolks.presentation.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcelldr.thefolks.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        fun binding() {
            fun DetailAccount() {
                binding.profileDetailButton.setOnClickListener {
                    val intent = Intent (this@ProfileFragment.context, DetailAccount()::class.java)
                    startActivity(intent)
                }
            }

            fun Setting(){
                binding.profileSettings.setOnClickListener {
                    val intent = Intent(this@ProfileFragment.context, SettingActivity::class.java)
                    startActivity(intent)
                }
            }

            fun About() {
                binding.profileAbout.setOnClickListener {
                    val intent = Intent(this@ProfileFragment.context, AboutAppActivity::class.java)
                    startActivity(intent)
                }
            }

            fun TermsCondition(){
                binding.profileTerms.setOnClickListener {
                    val intent = Intent(this@ProfileFragment.context, TermsConditionsActivity::class.java)
                    startActivity(intent)
                }
            }

            DetailAccount()
            Setting()
            About()
            TermsCondition()


        }

        binding()
    }
}