package no.shortcut.androidtemplate.common.service

interface RemoteConfig {

    suspend fun init(): RemoteConfig

    fun isVersionLocked(): Boolean
}
