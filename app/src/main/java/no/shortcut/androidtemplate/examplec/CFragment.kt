package no.shortcut.androidtemplate.examplec

import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseFragment
import no.shortcut.androidtemplate.databinding.FragmentCBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CFragment : BaseFragment<FragmentCBinding, CNavigation>() {
    override val layoutId: Int = R.layout.fragment_c
    override val viewModel: CViewModel by viewModel()

    override fun onNavigationEvent(event: CNavigation) {
    }
}
