package no.shortcut.androidtemplate.startup.splash

import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseFragment
import no.shortcut.androidtemplate.common.extensions.navigate
import no.shortcut.androidtemplate.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashNavigation>() {

    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun onNavigationEvent(event: SplashNavigation) {
        when (event) {
            SplashNavigationOnboarding -> navigate(
                SplashFragmentDirections.toOnboarding(),
                false
            )
            SplashNavigationVersionLock -> navigate(
                SplashFragmentDirections.toVersionLock(),
                false
            )
            SplashNavigationMain -> navigate(
                SplashFragmentDirections.toMain(),
                false
            )
        }
    }
}
