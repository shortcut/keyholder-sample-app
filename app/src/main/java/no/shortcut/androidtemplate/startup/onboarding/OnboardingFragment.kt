package no.shortcut.androidtemplate.startup.onboarding

import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseFragment
import no.shortcut.androidtemplate.common.extensions.navigate
import no.shortcut.androidtemplate.common.service.AnalyticsEvent
import no.shortcut.androidtemplate.common.service.AnalyticsService
import no.shortcut.androidtemplate.databinding.FragmentOnboardingBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingNavigation>() {

    override val layoutId: Int = R.layout.fragment_onboarding
    override val viewModel: OnboardingViewModel by viewModel()
    private val analyticsService: AnalyticsService by inject()

    override fun onNavigationEvent(event: OnboardingNavigation) {
        when (event) {
            is OnboardingFinish -> {
                analyticsService.logEvent(AnalyticsEvent.OnboardingComplete)
                navigate(
                    OnboardingFragmentDirections.toMain(),
                    false
                )
            }
        }
    }
}
