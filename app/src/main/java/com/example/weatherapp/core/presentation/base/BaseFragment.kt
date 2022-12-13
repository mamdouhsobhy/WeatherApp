package com.example.weatherapp.core.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.weatherapp.core.presentation.extensions.hideKeyboard
import com.example.weatherapp.core.presentation.view.dialog.ViewDialog


abstract class BaseFragment<BINDING : ViewBinding> : Fragment() {

    private var _binding: BINDING? = null
    val binding get() = _binding!!
    lateinit var progressDialog: ViewDialog

    protected abstract fun onBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ViewDialog(requireActivity())
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null)
            _binding = onBind(inflater, container)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showProgress() {
        progressDialog.showDialog()
    }

    fun hidProgress() {
        progressDialog.hideDialog()
    }

    fun <T : Any> setBackStackResult(key: String, data: T, doBack: Boolean = true) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
        if (doBack) {
            findNavController().popBackStack()
        }
    }

    fun <T : Any> getBackStackResult(key: String, result: (T) -> (Unit)) {
        val observer =
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        observer?.observe(viewLifecycleOwner) {
            it?.let {
                result(it)
            }
        }
        observer?.postValue(null)
    }

    override fun onDestroyView() {
        view?.hideKeyboard()
        progressDialog.hideDialog()
        super.onDestroyView()
//        _binding = null
    }
}