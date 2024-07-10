package com.thingsolver.android.sdk.event

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.thingsolver.android.sdk.eventsdk.Config
import com.thingsolver.android.sdk.eventsdk.EventSDK
import com.thingsolver.android.sdk.eventsdk.model.CollectionData
import com.thingsolver.android.sdk.eventsdk.model.Event
import com.thingsolver.android.sdk.event.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        EventSDK.initialize(this, Config("dev", "https://test-events.thingsolver.com", "4X47GtP8ne4Jo0qD8XQSf7M0LVUP56lr7K8IlAb6", 100, 20_000))

        lifecycleScope.launch {
            var i = 0
            while (true) {
                delay(5_000)
                EventSDK.collect(
                    CollectionData(
                        "abc123",
                        "cust456",
                        false,
                        "home",
                        "main$i", Event.APP_OPEN,
                        "success",
                        mapOf("arg1" to "value1", "arg2" to "value2"),
                        "en",
                        null,
                        null)
                )
                i++
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}