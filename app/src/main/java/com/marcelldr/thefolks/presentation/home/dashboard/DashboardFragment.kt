package com.marcelldr.thefolks.presentation.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.marcelldr.thefolks.data.dummy.DummyBanner
import com.marcelldr.thefolks.data.dummy.DummyDashboardButton
import com.marcelldr.thefolks.databinding.FragmentDashboardBinding
import com.marcelldr.thefolks.domain.model.Banner
import com.marcelldr.thefolks.domain.model.DashboardButton
import com.marcelldr.thefolks.domain.model.News
import com.marcelldr.thefolks.presentation.phone.PhoneActivity
import com.marcelldr.thefolks.presentation.upgrade.UpgradeP1Activity
import com.marcelldr.thefolks.vo.Resource
import com.thefinestartist.finestwebview.FinestWebView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var bannerRVAdapter: BannerRVAdapter
    private lateinit var dashboardButtonRVAdapter: DashboardButtonRVAdapter
    private lateinit var newsRVAdapter: NewsRVAdapter
    private val mAuth: FirebaseAuth by inject()
    private val db: FirebaseFirestore by inject()
    private val newsViewModel: NewsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        fun binding() {
            fun bannerBinding() {
                bannerRVAdapter = BannerRVAdapter(DummyBanner.listBanner)
                binding.dashboardBannerRV.adapter = bannerRVAdapter
                bannerRVAdapter.setOnItemClickCallback(object :
                    BannerRVAdapter.OnItemClickCallback {
                    override fun onItemClicked(banner: Banner) {
                        FinestWebView.Builder(requireActivity()).show(banner.linkUrl)
                    }
                })

                val pagerSnapHelper = PagerSnapHelper()
                pagerSnapHelper.attachToRecyclerView(binding.dashboardBannerRV)
                binding.dashboardBannerIndicator.attachToRecyclerView(
                    binding.dashboardBannerRV,
                    pagerSnapHelper
                )
                bannerRVAdapter.registerAdapterDataObserver(binding.dashboardBannerIndicator.adapterDataObserver)
            }

            fun dashboardButtonBinding() {
                dashboardButtonRVAdapter =
                    DashboardButtonRVAdapter(DummyDashboardButton.listDashboardButton)
                binding.dashboardButtonRV.adapter = dashboardButtonRVAdapter
                dashboardButtonRVAdapter.setOnItemClickCallback(object :
                    DashboardButtonRVAdapter.OnItemClickCallback {
                    override fun onItemClicked(dashboardButton: DashboardButton) {
                        if (dashboardButton.linkUrl != null) {
                            val intent =
                                Intent(requireActivity(), DashboardDetailActivity::class.java)
                            intent.putExtra(DashboardDetailActivity.EXTRA, dashboardButton)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Masih dalam pengembangan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }

            fun newsBinding() {
                lifecycleScope.launch {
                    newsViewModel.getNews().collectLatest {
                        when (it) {
                            is Resource.Loading -> binding.dashboardNewsLoading.visibility =
                                View.VISIBLE
                            is Resource.Error -> Toast.makeText(
                                requireContext(),
                                "News Error",
                                Toast.LENGTH_SHORT
                            )
                            is Resource.Success -> {
                                newsRVAdapter = NewsRVAdapter(it.data!!)
                                binding.dashboardNewsLoading.visibility = View.GONE
                                binding.dashboardNewsRV.layoutManager = GridLayoutManager(
                                    requireContext(),
                                    2,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                binding.dashboardNewsRV.adapter = newsRVAdapter
                                newsRVAdapter.setOnItemClickCallback(object :
                                    NewsRVAdapter.OnItemClickCallback {
                                    override fun onItemClicked(news: News) {
                                        FinestWebView.Builder(requireActivity()).show(news.url)
                                    }
                                })
                            }
                        }
                    }
                }
            }

            bannerBinding()
            dashboardButtonBinding()
            newsBinding()

            binding.dashboardLogout.setOnClickListener {
                mAuth.signOut()
                startActivity(Intent(requireActivity(), PhoneActivity::class.java))
                requireActivity().finish()
            }
            binding.dashboardUpgrade.setOnClickListener {
                startActivity(Intent(requireActivity(), UpgradeP1Activity::class.java))
            }

            db.collection("users").document(mAuth.currentUser?.uid.toString()).addSnapshotListener(
                MetadataChanges.INCLUDE
            ) { snapshot, _ ->
                val status = snapshot?.get("status")
                if (status == "verified") {
                    binding.dashboardUpgrade.visibility = View.GONE
                } else {
                    binding.dashboardUpgrade.visibility = View.VISIBLE
                }
            }
        }

        binding()
    }
}