package no.shortcut.androidtemplate.common.service

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsService(context: Context) : AnalyticsService {

    private val instance by lazy { FirebaseAnalytics.getInstance(context) }

    override fun logEvent(event: AnalyticsEvent) {
        instance.logEvent(event.key, Bundle())
    }
}
