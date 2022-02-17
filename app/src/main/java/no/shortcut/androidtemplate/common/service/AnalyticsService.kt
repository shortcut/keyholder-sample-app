package no.shortcut.androidtemplate.common.service

interface AnalyticsService {
    fun logEvent(event: AnalyticsEvent)
}

enum class AnalyticsEvent(val key: String) {
    OnboardingComplete("onboarding_complete")
}
