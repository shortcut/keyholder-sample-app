
package no.shortcut.androidtemplate.startup.onboarding

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import no.shortcut.androidtemplate.BR
import no.shortcut.androidtemplate.R
import no.shortcut.androidtemplate.common.base.BaseViewModel
import no.shortcut.androidtemplate.common.service.PreferenceService

class OnboardingViewModel(
    context: Context,
    private val preferenceService: PreferenceService
) : BaseViewModel<OnboardingNavigation>() {

    val currentPage = MutableStateFlow(0)

    val pages = MutableStateFlow(
        listOf(
            OnboardingPage(
                context.getString(R.string.onboarding_1_title),
                context.getString(R.string.onboarding_1_text),
                ContextCompat.getDrawable(context, R.drawable.ic_launcher_background),
                context.getString(R.string.onboarding_button)
            ),
            OnboardingPage(
                context.getString(R.string.onboarding_2_title),
                context.getString(R.string.onboarding_2_text),
                ContextCompat.getDrawable(context, R.drawable.ic_launcher_background),
                context.getString(R.string.onboarding_button)
            ),
            OnboardingPage(
                context.getString(R.string.onboarding_3_title),
                context.getString(R.string.onboarding_3_text),
                ContextCompat.getDrawable(context, R.drawable.ic_launcher_background),
                context.getString(R.string.onboarding_button_last)
            )
        )
    )

    val buttonText: StateFlow<CharSequence> =
        combine(currentPage, pages) { currentPage, pages ->
            pages[currentPage].buttonText
        }.asState("")

    val itemBinding: ItemBinding<OnboardingPage> =
        ItemBinding.of(BR.onboardingPage, R.layout.onboarding_page)

    fun nextPage() {
        val current = currentPage.value
        if (current < pages.value.lastIndex) {
            currentPage.value = currentPage.value.inc()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                preferenceService.setOnboardingCompleted(true)
                navigate(OnboardingFinish)
            }
        }
    }
}
