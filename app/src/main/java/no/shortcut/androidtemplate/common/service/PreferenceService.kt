package no.shortcut.androidtemplate.common.service

import kotlinx.coroutines.flow.Flow

interface PreferenceService {
    val onboardingCompleted: Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)
}
