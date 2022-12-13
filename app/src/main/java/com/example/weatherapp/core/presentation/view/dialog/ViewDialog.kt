package com.example.weatherapp.core.presentation.view.dialog


import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.weatherapp.R

class ViewDialog(var activity: Activity) {


    lateinit var dialog: Dialog

    init {
        drawDialog()
    }

    private fun drawDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false)
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.layout_custom_loading)
    }

    //..also create a method which will hide the dialog when some work is done
    fun hideDialog() {
        if (this::dialog.isInitialized && dialog.isShowing)
            dialog.dismiss()
    }

    fun showDialog() {
        if (this::dialog.isInitialized && dialog.isShowing.not())
            dialog.show()
    }

}