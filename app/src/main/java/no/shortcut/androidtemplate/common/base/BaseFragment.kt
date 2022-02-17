package no.shortcut.androidtemplate.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import no.shortcut.androidtemplate.BR

abstract class BaseFragment<T : ViewDataBinding, E> : Fragment() {
    protected lateinit var binding: T
    protected abstract val viewModel: BaseViewModel<E>?
    protected abstract val layoutId: Int
    protected open val hasOptionsMenu = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel?.let { viewModel ->
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
        if (hasOptionsMenu) setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.navigation?.observe { event ->
            onNavigationEvent(event)
        }
    }

    abstract fun onNavigationEvent(event: E)

    protected fun <T> Flow<T>.observe(callback: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@observe.collect {
                    callback(it)
                }
            }
        }
    }
}
