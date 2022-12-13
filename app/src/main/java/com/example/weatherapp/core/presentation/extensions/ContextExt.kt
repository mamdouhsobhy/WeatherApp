package com.example.weatherapp.core.presentation.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherapp.R
import es.dmoral.toasty.Toasty

fun Context.showToast(message: String, connectionError: Boolean=false){
    if (connectionError){
        Toasty.error(this, getString(R.string.check_internet_connections), Toast.LENGTH_SHORT, true).show()

    }else{
        Log.d("errror",message)
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
    }
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("ok"){ dialog, _ ->
             dialog.dismiss()
        }
    }.show()
}