package no.shortcut.androidtemplate.startup.splash

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.shortcut.androidtemplate.common.base.BaseViewModel
import no.shortcut.androidtemplate.common.service.PreferenceService
import no.shortcut.androidtemplate.common.service.RemoteConfig

class SplashViewModel(
    remoteConfig: RemoteConfig,
    preferences: PreferenceService
) : BaseViewModel<SplashNavigation>() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            remoteConfig.init()
            when {
                remoteConfig.isVersionLocked() -> navigate(
                    SplashNavigationVersionLock
                )
                !preferences.onboardingCompleted.first() -> navigate(
                    SplashNavigationOnboarding
                )
                else -> navigate(SplashNavigationMain)
            }
        }
    }
}
