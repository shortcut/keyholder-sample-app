package no.shortcut.androidtemplate.startup

import no.shortcut.androidtemplate.startup.onboarding.OnboardingViewModel
import no.shortcut.androidtemplate.startup.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val startupModule = module {
    viewModel<OnboardingViewModel>()
    viewModel<SplashViewModel>()
}
