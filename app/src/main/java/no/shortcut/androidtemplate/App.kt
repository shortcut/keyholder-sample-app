package no.shortcut.androidtemplate

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import no.shortcut.androidtemplate.common.commonModule
import no.shortcut.androidtemplate.examplea.aModule
import no.shortcut.androidtemplate.exampleb.bModule
import no.shortcut.androidtemplate.examplec.cModule
import no.shortcut.androidtemplate.main.mainModule
import no.shortcut.androidtemplate.startup.startupModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(mainModule, commonModule, startupModule, aModule, bModule, cModule))
        }
        initLogging()
    }

    private fun initLogging() {
        val min = if (BuildConfig.DEBUG) Log.VERBOSE else Log.DEBUG
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree(min))
    }

    private class CrashReportingTree(private val minPriority: Int) : Timber.Tree() {
        override fun isLoggable(tag: String?, priority: Int): Boolean = priority > minPriority
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            super.log(priority, tag, message, t)
            val inst = FirebaseCrashlytics.getInstance()
            if (t != null) {
                inst.recordException(t)
            } else {
                inst.log("$message [priority=$priority, tag=$tag]")
            }
        }
    }
}
