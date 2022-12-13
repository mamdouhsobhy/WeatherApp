package com.example.weatherapp.core.presentation.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.weatherapp.databinding.ActivityBaseBinding
import com.example.weatherapp.core.presentation.utilities.LocaleHelper
import com.example.weatherapp.core.presentation.view.dialog.ViewDialog
import com.example.weatherapp.core.presentation.extensions.hideKeyboard
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

     val binding by lazy { initBinding() }
    private val baseBinding by lazy { ActivityBaseBinding.inflate(layoutInflater) }
    lateinit var progessDialog: ViewDialog

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setContent()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    private fun setContent() {
        baseBinding.flContainer.addView(binding.root)
        setContentView(baseBinding.root)
    }

    override fun onPause() {
        hideKeyboard(currentFocus)
        super.onPause()
    }

    fun showProgress() {
        if (!this::progessDialog.isInitialized)
            progessDialog = ViewDialog(this)
        progessDialog.showDialog()
    }

    fun hidProgress() {
        progessDialog.hideDialog()
    }

}