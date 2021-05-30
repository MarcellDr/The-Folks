package com.marcelldr.thefolks.presentation.home.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityDashboardDetailBinding
import com.marcelldr.thefolks.domain.model.DashboardButton
import com.thefinestartist.finestwebview.FinestWebView

class DashboardDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardDetailBinding

    companion object {
        const val EXTRA = "extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        val dashboardButton = intent.getParcelableExtra<DashboardButton>(EXTRA) as DashboardButton
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        fun binding() {
            binding = ActivityDashboardDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.detailButton.text =
                StringBuilder("Buka Website ").append(dashboardButton.title).append(" Online")
            binding.detailDescription.text = dashboardButton.description
            Glide.with(binding.detailLogo.context).load(dashboardButton.logoUrl)
                .into(binding.detailLogo)

            binding.detailBackButton.setOnClickListener { finish() }
            binding.detailButton.setOnClickListener {
                FinestWebView.Builder(this).show(dashboardButton.linkUrl!!)
            }
        }

        statusAndActionBar()
        binding()
    }
}