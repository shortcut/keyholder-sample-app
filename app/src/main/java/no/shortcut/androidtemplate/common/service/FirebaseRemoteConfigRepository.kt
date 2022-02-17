package no.shortcut.androidtemplate.common.service

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import no.shortcut.androidtemplate.BuildConfig
import no.shortcut.androidtemplate.R
import timber.log.Timber
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRemoteConfigRepository :
    RemoteConfig {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    override suspend fun init(): RemoteConfig =
        suspendCoroutine { c ->
            remoteConfig.setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setFetchTimeoutInSeconds(5)
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build()
            )
            remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Firebase Remote Config params updated")
                    } else {
                        Timber.e(task.exception, "Firebase Remote Config fetch failed")
                    }
                    c.resume(this)
                }
        }

    override fun isVersionLocked(): Boolean {
        return try {
            remoteConfig.getString("require_update_android").split(Regex(","))
                .contains(BuildConfig.VERSION_CODE.toString())
        } catch (ex: Exception) {
            false
        }
    }
}
