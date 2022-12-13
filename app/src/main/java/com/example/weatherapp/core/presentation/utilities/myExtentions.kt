package com.example.weatherapp.core.presentation.utilities

import android.util.Patterns
import android.widget.EditText

fun EditText.isPhone(msg: String): Boolean {
    val p = "^1([34578])\\d{9}\$".toRegex()
    if (!this.text.toString().matches(p)) {
        this.setError(msg)
    }
    return this.text.toString().matches(p)
}

fun EditText.isEmail(msg: String): Boolean {

    val txt=this.text.toString()

    if (txt.isNullOrEmpty() ||! Patterns.EMAIL_ADDRESS.matcher(txt).matches()) {
        this.setError(msg)
    }

    return  !txt.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(txt).matches()
}

fun EditText.isEmpty(msg: String): Boolean {
    if (this.text.toString().isEmpty()) {
        this.error = msg
    }
    return this.text.toString().isEmpty()
}
fun EditText.isPasswordMatch(passwrod: String, msg: String): Boolean {
    if (this.text.toString().isEmpty()||!this.text.toString().equals(passwrod)) {
        this.setError(msg)
    }
    return this.text.toString().equals(passwrod)
}





