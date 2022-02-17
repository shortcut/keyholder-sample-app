package no.shortcut.androidtemplate.startup.onboarding

import android.graphics.drawable.Drawable

data class OnboardingPage(
    val title: String,
    val text: String,
    val image: Drawable?,
    val buttonText: CharSequence
)
