package no.shortcut.androidtemplate.main

import android.content.Context
import androidx.annotation.IdRes
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import no.shortcut.androidtemplate.R

class MainViewModel(
    private val context: Context
) : ViewModel() {
    val currentFragment = MutableStateFlow<@IdRes Int>(R.id.splash_fragment)

    val topLevelDestinations = setOf<@IdRes Int>(
        R.id.a_fragment
    )

    val appbarVisible: StateFlow<Boolean> = currentFragment.map { id ->
        listOf<@IdRes Int>(
            R.id.onboarding_fragment,
            R.id.splash_fragment,
            R.id.versionlock_fragment
        ).contains(id).not()
    }.asState(false)

    val toolbarTitle: StateFlow<CharSequence> = currentFragment.map { id ->
        when (id) {
            R.id.a_fragment -> "Toolbar title"
            R.id.c_fragment -> "Toolbar title"
            else -> "Back"
        }
    }.asState("")

    val bottomNavVisible: StateFlow<Boolean> = currentFragment.map { id ->
        listOf<@IdRes Int>(
            R.id.a_fragment,
            R.id.c_fragment
        ).contains(id)
    }.asState(false)

    private fun <T> Flow<T>.asState(initialValue: T): StateFlow<T> {
        return this.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue)
    }
}
