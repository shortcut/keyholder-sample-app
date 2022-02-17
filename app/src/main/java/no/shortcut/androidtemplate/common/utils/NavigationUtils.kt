package no.shortcut.androidtemplate.common.utils

import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import no.shortcut.androidtemplate.R

object NavigationUtils {
    private val defaultNavOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    fun getNavOptions(directions: NavDirections, destination: NavDestination?): NavOptions =
        destination?.getAction(directions.actionId)?.let { navAction ->
            navAction.navOptions?.let { navOptions ->
                val hasNoAnims = navOptions.enterAnim == -1 && navOptions.exitAnim == -1 &&
                    navOptions.popEnterAnim == -1 && navOptions.popExitAnim == -1

                if (hasNoAnims) {
                    navAction.navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(navOptions.shouldLaunchSingleTop())
                        .setPopUpTo(navOptions.popUpTo, navOptions.isPopUpToInclusive)
                        .setEnterAnim(defaultNavOptions.enterAnim)
                        .setExitAnim(defaultNavOptions.exitAnim)
                        .setPopEnterAnim(defaultNavOptions.popEnterAnim)
                        .setPopExitAnim(defaultNavOptions.popExitAnim)
                        .build()
                }
                navAction.navOptions
            } ?: defaultNavOptions
        } ?: defaultNavOptions
}
