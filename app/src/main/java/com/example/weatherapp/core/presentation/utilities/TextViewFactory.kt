package com.example.weatherapp.core.presentation.utilities

import android.content.Context
import androidx.annotation.StyleRes
import android.widget.ViewSwitcher
import android.widget.TextView
import android.view.Gravity
import android.os.Build
import android.view.View

class TextViewFactory(
    private val context: Context,
    @field:StyleRes @param:StyleRes private val styleId: Int,
    private val center: Boolean
) : ViewSwitcher.ViewFactory {
    override fun makeView(): View {
        val textView = TextView(context)
        if (center) {
            textView.gravity = Gravity.CENTER
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, styleId)
        } else {
            textView.setTextAppearance(styleId)
        }
        return textView
    }
}