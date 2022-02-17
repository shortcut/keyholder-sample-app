package no.shortcut.androidtemplate.common.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import no.shortcut.androidtemplate.common.utils.NavigationUtils

fun Fragment.navigate(directions: NavDirections, useProjectNavOptions: Boolean = true) {
    val controller = findNavController()
    val currentDestination =
        when (val destination = controller.currentDestination) {
            is FragmentNavigator.Destination -> destination.className
            is DialogFragmentNavigator.Destination -> destination.className
            else -> null
        }

    if (currentDestination == this.javaClass.name) {
        if (useProjectNavOptions) {
            controller.navigate(
                directions,
                NavigationUtils.getNavOptions(directions, controller.currentDestination)
            )
        } else {
            controller.navigate(directions)
        }
    }
}
