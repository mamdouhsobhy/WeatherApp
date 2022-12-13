package com.example.weatherapp.core.presentation.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.weatherapp.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(rootView?.windowToken, 0)
}

fun Context.hideKeyboard(view: View?) = view?.let {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(this.context)

@SuppressLint("NewApi", "SimpleDateFormat")
fun String.reFormateDate(pattern: String? = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"): String {
    val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
    var Date1: Date? = null
    var Date2: Date? = null
    var count = 0
    try {
        var currentDate = Date()
        val formattedCurrentDate: String = sdf.format(currentDate)
        Date1 = sdf.parse(this)
        Date2 = sdf.parse(formattedCurrentDate)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val diff = Date2!!.time - Date1!!.time
    var dateFormat = ""
//    val secondsAgo = diff / 1000
//    val minutes = 60
//    val hour = 60 * minutes
//    val day = 24 * hour
//    val week = 7 * day
//    val month = 4 + week

    val suffix = "Ago"

    val second: Long = TimeUnit.MILLISECONDS.toSeconds(diff)
    val minute: Long = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hour: Long = TimeUnit.MILLISECONDS.toHours(diff)
    val day: Long = TimeUnit.MILLISECONDS.toDays(diff)

    if (second < 60) {
//        dateFormat = "$second Seconds $suffix"
        dateFormat = "Just Now"
    } else if (minute < 60) {
        if (minute > 1) {
            dateFormat = "$minute Minutes $suffix"

        } else {
            dateFormat = "$minute Minute $suffix"

        }
    } else if (hour < 24) {
        if (hour > 1) {
            dateFormat = "$hour Hours $suffix"

        } else {
            dateFormat = "$hour Hour $suffix"

        }
    } else if (day >= 7) {
        if (day > 360) {
            if (day / 360 > 1) {
                dateFormat = (day / 360).toString() + " Years " + suffix

            } else {
                dateFormat = (day / 360).toString() + " Year " + suffix

            }
        } else if (day > 30) {
            if (day / 30 > 1) {
                dateFormat = (day / 30).toString() + " Months " + suffix
            } else {
                dateFormat = (day / 30).toString() + " Month " + suffix
            }
        } else {
            if (day / 7 > 1) {
                dateFormat = (day / 7).toString() + " Weeks " + suffix

            } else {
                dateFormat = (day / 7).toString() + " Week " + suffix

            }
        }
    } else if (day < 7) {
        if (day>1){
            dateFormat = "$day Days $suffix"

        }else{
            dateFormat = "$day Day $suffix"
        }
    }



    return dateFormat
}


@Throws(Throwable::class)
fun getImageFromVideo(videoPath: String): Bitmap {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(videoPath)
    return retriever.getFrameAtTime(1000000)!!
}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage,
            "Title", null)
    return Uri.parse(path)
}

fun getFileSize(filePath: String?): Double {
    val file = File(filePath)
    val fileSize = (file.length() / 1024).toString().toInt().toDouble()
    return fileSize / 1024
}

fun Context.shareToSocialMedia(shareText: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, shareText)
    startActivity(Intent.createChooser(intent, "Share"))
}

fun Context.shareAppToSocialMedia(shareText: String) {

    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Circle Hub")
        var shareMessage = shareText
        shareMessage =
            """ $shareMessage https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID} """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    } catch (e: Exception) {
    }
}
