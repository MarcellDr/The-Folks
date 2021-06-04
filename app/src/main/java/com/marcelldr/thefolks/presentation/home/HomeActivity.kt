package com.marcelldr.thefolks.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityHomeBinding
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val mAuth: FirebaseAuth by inject()
    private val db: FirebaseFirestore by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        fun bottomNavigation() {
            val hostFragment =
                supportFragmentManager.findFragmentById(R.id.homeHostFragment) as NavHostFragment
            binding.homeBottomNav.setupWithNavController(hostFragment.navController)

            db.collection("users").document(mAuth.currentUser?.uid.toString()).addSnapshotListener(
                MetadataChanges.INCLUDE) { snapshot, _ ->
                val status = snapshot?.get("status")
                if (status == "verified") {
                    binding.homeBottomNav.visibility = View.VISIBLE
                } else {
                    binding.homeBottomNav.visibility = View.GONE
                }
            }
        }

        fun binding() {
            binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)
            bottomNavigation()
        }

        statusAndActionBar()
        binding()
    }
}