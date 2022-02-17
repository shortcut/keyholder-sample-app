package no.shortcut.androidtemplate.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    private val _navigation = Channel<T>(Channel.BUFFERED)
    val navigation = _navigation.receiveAsFlow()

    fun navigate(event: T) {
        viewModelScope.launch {
            _navigation.send(event)
        }
    }

    protected fun <T> Flow<T>.asState(initialValue: T): StateFlow<T> {
        return this.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue)
    }
}
