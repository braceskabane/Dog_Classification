package com.dicoding.asclepius.view.dashboard

import android.Manifest
//import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        setupActionBar()

        setupBottomNavbar()

    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    private fun setupBottomNavbar() {
        val navView: BottomNavigationView = binding.bottomNavigation
        val navViewController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.homeFragment,
                R.id.historyFragment,
                R.id.favoriteFragment,
                R.id.profileFragment
            )
        ).build()

        navView.setupWithNavController(navViewController)

        navView.setOnNavigationItemSelectedListener { item ->
            val currentDestination = navViewController.currentDestination?.id
            val currentIndex = getMenuIndex(currentDestination)
            val targetIndex = getMenuIndex(item.itemId)

            val navBuilder = if (targetIndex > currentIndex) {
                NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
            } else {
                NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_left)
                    .setExitAnim(R.anim.slide_out_right)
            }
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (currentDestination != R.id.homeFragment) {
                        navViewController.navigate(R.id.homeFragment, null, navBuilder.build())
                    }
                }
                R.id.historyFragment -> {
                    if (currentDestination != R.id.historyFragment) {
                        navViewController.navigate(R.id.historyFragment, null, navBuilder.build())
                    }
                }
                R.id.favoriteFragment -> {
                    if (currentDestination != R.id.favoriteFragment) {
                        navViewController.navigate(R.id.favoriteFragment, null, navBuilder.build())
                    }
                }
                R.id.profileFragment -> {
                    if (currentDestination != R.id.profileFragment) {
                        navViewController.navigate(R.id.profileFragment, null, navBuilder.build())
                    }
                }
            }
            true
        }
    }

    private fun getMenuIndex(itemId: Int?): Int {
        return when (itemId) {
            R.id.homeFragment -> 0
            R.id.historyFragment -> 1
            R.id.favoriteFragment -> 2
            R.id.profileFragment -> 3
            else -> -1
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}