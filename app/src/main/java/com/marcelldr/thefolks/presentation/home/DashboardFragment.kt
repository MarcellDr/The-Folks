package com.marcelldr.thefolks.presentation.home

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcelldr.thefolks.R
import com.synnapps.carouselview.CarouselView
import com.marcelldr.thefolks.databinding.FragmentDashboardBinding
import com.marcelldr.thefolks.databinding.ContentCarouselBinding

class DashboardFragment : Fragment() {


    var sampleImages = intArrayOf(
        R.drawable.carousel_bpjs,
        R.drawable.carousel_kip,
        R.drawable.carousel_prakerjaa
    )


    private var binding : FragmentDashboardBinding? = null
    private val binding_ get() = binding!!
    private var bindingCarousel : ContentCarouselBinding? = null
    private val bindingCarousel_ get() = bindingCarousel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding_.root

        val carouselLayout = bindingCarousel?.carouselView
        carouselLayout.pageCount = sampleImages.size
        carouselLayout.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }


    }


}