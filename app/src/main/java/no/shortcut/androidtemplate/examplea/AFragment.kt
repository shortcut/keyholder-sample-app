package no.shortcut.androidtemplate.examplea

import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseFragment
import no.shortcut.androidtemplate.common.extensions.navigate
import no.shortcut.androidtemplate.databinding.FragmentABinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AFragment : BaseFragment<FragmentABinding, ANavigation>() {
    override val layoutId: Int = R.layout.fragment_a
    override val viewModel: AViewModel by viewModel()

    override fun onNavigationEvent(event: ANavigation) {
        when (event) {
            is ASubmit -> navigate(AFragmentDirections.toB())
        }
    }
}
