package com.kiltonik.tiresapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.kiltonik.tiresapp.R
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<
        ViewState,
        Action,
        ViewModel: BaseViewModel<Action, ViewState>,
        Binding: ViewBinding
        >
    : Fragment(), BaseView<ViewState>{

    private lateinit var rootView: ViewGroup

    private var viewModel = lazy {
        ViewModelProvider(requireActivity()).get(getViewModelClass())
    }

    protected lateinit var binding: Binding

    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<ViewModel> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[2]
        return type as Class<ViewModel>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_base, container, false)
                as ViewGroup

        val contentContainer = rootView.findViewById<ViewGroup>(R.id.content_container)
        binding = getBinding(
            inflater = inflater,
            container = container
        )
        contentContainer.addView(binding.root)

        viewModel.value.publicViewState.observe(viewLifecycleOwner, {
            renderViewState(it)
        })
        return rootView
    }

    private fun renderViewState(viewState: BaseViewState<ViewState>) {
        when(viewState){
            is BaseViewState.Message -> showErrorMessage(viewState.message)

            is BaseViewState.Error -> showFatalError(viewState.throwable)

            is BaseViewState.Loading -> showLoading()

            is BaseViewState.Other<ViewState> -> {
                showContent()
                render(viewState.content)
            }
        }
    }

    protected fun dispatchAction(action: Action) = viewModel.value.dispatch(action)


    private fun showErrorMessage(error: String){
        showContent()
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    private fun showFatalError(error: Throwable){
        rootView.findViewById<ViewGroup>(R.id.content_container).visibility = INVISIBLE
        rootView.findViewById<ViewGroup>(R.id.error_container).visibility = VISIBLE
        rootView.findViewById<ProgressBar>(R.id.progress_bar).visibility = INVISIBLE
        rootView.findViewById<TextView>(R.id.error_message).text = error.localizedMessage
    }

    private fun showLoading(){
        rootView.findViewById<ViewGroup>(R.id.content_container).visibility = INVISIBLE
        rootView.findViewById<ViewGroup>(R.id.error_container).visibility = INVISIBLE
        rootView.findViewById<ProgressBar>(R.id.progress_bar).visibility = VISIBLE
    }

    private fun showContent(){
        rootView.findViewById<ViewGroup>(R.id.content_container).visibility = VISIBLE
        rootView.findViewById<ViewGroup>(R.id.error_container).visibility = INVISIBLE
        rootView.findViewById<ProgressBar>(R.id.progress_bar).visibility = INVISIBLE
    }

}