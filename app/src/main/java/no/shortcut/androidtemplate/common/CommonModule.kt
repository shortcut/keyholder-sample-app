package no.shortcut.androidtemplate.common

import no.shortcut.androidtemplate.common.service.*
import org.koin.dsl.module

val commonModule = module {
    single<RemoteConfig> { FirebaseRemoteConfigRepository() }
    single<PreferenceService> { DataStorePreferences(get()) }
    single<AnalyticsService> { FirebaseAnalyticsService(get()) }
    single<ReviewService> { GoogleReviewService(get()) }
}
