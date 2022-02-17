package no.shortcut.androidtemplate.startup.splash

sealed interface SplashNavigation
object SplashNavigationVersionLock : SplashNavigation
object SplashNavigationOnboarding : SplashNavigation
object SplashNavigationMain : SplashNavigation
