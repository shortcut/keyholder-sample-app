package no.shortcut.androidtemplate.common.service

import android.app.ActivityManager
import android.app.KeyguardManager
import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Handle data notifications ?
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Register token
    }

    companion object {
        suspend fun getFirebaseMessagingToken(): String? =
            suspendCoroutine { c ->
                FirebaseMessaging.getInstance().token
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            c.resume(it.result)
                        } else {
                            Timber.e(it.exception, "Unsuccessful at retrieving token: ")
                            c.resume(null)
                        }
                    }
            }
    }

    private fun isAppInForeground(): Boolean {
        val myProcess = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(myProcess)
        return myProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
    }

    private fun isScreenLocked(context: Context): Boolean =
        (context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).isKeyguardLocked
}
