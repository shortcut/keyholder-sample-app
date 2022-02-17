package no.shortcut.androidtemplate.main

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbar)

        // Suspend drawing until splash screen is done
        binding.root.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean =
                    when (viewModel.currentFragment.value) {
                        R.id.splash_fragment -> false
                        else -> {
                            binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        }
                    }
            }
        )
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val navController = Navigation.findNavController(this, R.id.menu_nav_host).apply {
            addOnDestinationChangedListener { _, destination, _ ->
                viewModel.currentFragment.value = destination.id
            }
            binding.bottomNav.setupWithNavController(this)
        }
        AppBarConfiguration(
            topLevelDestinationIds = viewModel.topLevelDestinations
        ).also { appbarConfig ->
            binding.toolbar.setupWithNavController(navController, appbarConfig)
        }
    }
}
