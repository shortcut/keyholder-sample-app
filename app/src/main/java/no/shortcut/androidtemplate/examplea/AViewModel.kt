package no.shortcut.androidtemplate.examplea

import no.shortcut.androidtemplate.common.base.BaseViewModel

class AViewModel : BaseViewModel<ANavigation>() {

    fun submit() {
        navigate(ASubmit)
    }
}
