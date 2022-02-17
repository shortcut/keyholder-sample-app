package no.shortcut.androidtemplate.startup.versionlock

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import no.shortcut.androidtemplate.BuildConfig
import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseFragment
import no.shortcut.androidtemplate.databinding.FragmentVersionLockBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VersionLockFragment : BaseFragment<FragmentVersionLockBinding, VersionLockNavigation>() {
    override val viewModel: VersionLockViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_version_lock

    override fun onNavigationEvent(event: VersionLockNavigation) {
        when (event) {
            is VersionLockUpdate -> updateApp()
        }
    }

    private fun updateApp() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$${BuildConfig.APPLICATION_ID}")
                )
            )
        }
    }
}
