package no.shortcut.androidtemplate.examplea

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val aModule = module {
    viewModel<AViewModel>()
}
