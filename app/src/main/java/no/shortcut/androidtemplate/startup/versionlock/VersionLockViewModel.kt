package no.shortcut.androidtemplate.startup.versionlock

import no.shortcut.androidtemplate.common.base.BaseViewModel

class VersionLockViewModel : BaseViewModel<VersionLockNavigation>() {

    fun updateApp() {
        navigate(VersionLockUpdate)
    }
}
