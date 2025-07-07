package com.example.recipemanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.recipemanager.databinding.ActivityMainBinding
import com.example.recipemanager.work.MealPlanReminderWorker
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val auth by lazy { FirebaseAuth.getInstance() }

    // Launcher for the POST_NOTIFICATIONS permission request
    private val notifPermissionLauncher =
        registerForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                scheduleDailyMealPlanReminder()
            } else {
                // Optionally inform the user that notifications won't appear
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) NavController & dynamic startDestination
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(
            if (auth.currentUser != null) R.id.recipesFragment
            else R.id.loginFragment
        )
        navController.graph = graph

        // 2) BottomNavigation hookup + show/hide on auth screens
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, dest, _ ->
            binding.bottomNavigation.isVisible = dest.id !in listOf(
                R.id.loginFragment,
                R.id.registerFragment
            )
        }

        // 3) Request notification permission then schedule
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    scheduleDailyMealPlanReminder()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Optionally explain why you need notifications, then request:
                    notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Pre-Android 13: no runtime permission needed
            scheduleDailyMealPlanReminder()
        }
    }

    private fun scheduleDailyMealPlanReminder() {
        // Calculate initial delay so it fires at ~8 AM next
        val now = Calendar.getInstance()
        val next8am = now.clone() as Calendar
        next8am.apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
        }
        val initialDelay = next8am.timeInMillis - now.timeInMillis

        // Build a one-day periodic request
        val workRequest = PeriodicWorkRequestBuilder<MealPlanReminderWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()

        // Enqueue uniquely so we don’t duplicate on each launch
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "daily_mealplan_reminder",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}
