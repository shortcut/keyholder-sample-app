package no.shortcut.androidtemplate.exampleb

import android.view.*
import com.google.android.material.snackbar.Snackbar
import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseFragment
import no.shortcut.androidtemplate.common.extensions.navigate
import no.shortcut.androidtemplate.databinding.FragmentBBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BFragment : BaseFragment<FragmentBBinding, BNavigation>() {
    override val layoutId: Int = R.layout.fragment_b
    override val viewModel: BViewModel by viewModel()
    override val hasOptionsMenu: Boolean = true

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        menu.add("FragmentB option").apply {
            setIcon(R.drawable.ic_baseline_message_24)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            setOnMenuItemClickListener {
                Snackbar.make(
                    requireView(),
                    "FragmentB has an options menu created with setHasOptionsMenu(true) and 'override onCreateOptionsMenu'. A global menu could be created in main activity",
                    Snackbar.LENGTH_LONG
                ).show()
                true
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onNavigationEvent(event: BNavigation) {
        when (event) {
            is BSubmit -> navigate(BFragmentDirections.toMain())
        }
    }
}
