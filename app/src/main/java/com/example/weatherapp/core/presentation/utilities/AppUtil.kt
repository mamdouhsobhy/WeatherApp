package com.example.weatherapp.core.presentation.utilities

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import android.text.TextUtils
import android.os.Build
import android.text.Html
import androidx.core.os.ConfigurationCompat
import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.RequiresPermission
import android.Manifest.permission
import android.content.Context
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.lang.NullPointerException
import java.lang.reflect.InvocationTargetException
import java.util.*

object AppUtil {
    private var fastOutSlowIn: Interpolator? = null

    /**
     * Get timestamp of start of day 00:00:00
     *
     * @param calendar instance of [Calendar]
     * @return timestamp
     */
    fun getStartOfDayTimestamp(calendar: Calendar): Long {
        val newCalendar = Calendar.getInstance(TimeZone.getDefault())
        newCalendar.timeInMillis = calendar.timeInMillis
        newCalendar[Calendar.HOUR_OF_DAY] = 0
        newCalendar[Calendar.MINUTE] = 0
        newCalendar[Calendar.SECOND] = 0
        newCalendar[Calendar.MILLISECOND] = 0
        return newCalendar.timeInMillis
    }

    /**
     * Get timestamp of end of day 23:59:59
     *
     * @param calendar instance of [Calendar]
     * @return timestamp
     */
    fun getEndOfDayTimestamp(calendar: Calendar): Long {
        val newCalendar = Calendar.getInstance(TimeZone.getDefault())
        newCalendar.timeInMillis = calendar.timeInMillis
        newCalendar[Calendar.HOUR_OF_DAY] = 23
        newCalendar[Calendar.MINUTE] = 59
        newCalendar[Calendar.SECOND] = 59
        newCalendar[Calendar.MILLISECOND] = 0
        return newCalendar.timeInMillis
    }

    /**
     * Add days to calendar and return result
     *
     * @param cal  instance of [Calendar]
     * @param days number of days
     * @return instance of [Calendar]
     */
    fun addDays(cal: Calendar, days: Int): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = cal.timeInMillis
        calendar.add(Calendar.DATE, days)
        return calendar
    }

    /**
     * Set icon to imageView according to weather code status
     *
     * @param context     instance of [Context]
     * @param imageView   instance of [android.widget.ImageView]
     * @param weatherCode code of weather status
     */
    fun setWeatherIcon(context: Context?, imageView: AppCompatImageView?, weatherCode: Int) {
        if (weatherCode / 100 == 2) {
            Glide.with(context!!).load(R.drawable.ic_storm_weather).into(imageView!!)
        } else if (weatherCode / 100 == 3) {
            Glide.with(context!!).load(R.drawable.ic_rainy_weather).into(imageView!!)
        } else if (weatherCode / 100 == 5) {
            Glide.with(context!!).load(R.drawable.ic_rainy_weather).into(imageView!!)
        } else if (weatherCode / 100 == 6) {
            Glide.with(context!!).load(R.drawable.ic_snow_weather).into(imageView!!)
        } else if (weatherCode / 100 == 7) {
            Glide.with(context!!).load(R.drawable.ic_unknown).into(imageView!!)
        } else if (weatherCode == 800) {
            Glide.with(context!!).load(R.drawable.ic_clear_day).into(imageView!!)
        } else if (weatherCode == 801) {
            Glide.with(context!!).load(R.drawable.ic_few_clouds).into(imageView!!)
        } else if (weatherCode == 803) {
            Glide.with(context!!).load(R.drawable.ic_broken_clouds).into(imageView!!)
        } else if (weatherCode / 100 == 8) {
            Glide.with(context!!).load(R.drawable.ic_cloudy_weather).into(imageView!!)
        }
    }

    /**
     * Show fragment with fragment manager with animation parameter
     *
     * @param fragment        instance of [Fragment]
     * @param fragmentManager instance of [FragmentManager]
     * @param withAnimation   boolean value
     */
    fun showFragment(
        fragment: Fragment?,
        fragmentManager: FragmentManager,
        withAnimation: Boolean
    ) {
        val transaction = fragmentManager.beginTransaction()
        if (withAnimation) {
            transaction.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim)
        } else {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        transaction.add(android.R.id.content, fragment!!).addToBackStack(null).commit()
    }

    /**
     * Get time of calendar as 00:00 format
     *
     * @param calendar instance of [Calendar]
     * @param context  instance of [Context]
     * @return string value
     */
    fun getTime(calendar: Calendar, context: Context): String {
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val hourString: String
        hourString = if (hour < 10) {
            String.format(
                Locale.getDefault(),
                context.getString(R.string.zero_label),
                hour
            )
        } else {
            String.format(Locale.getDefault(), "%d", hour)
        }
        val minuteString: String
        minuteString = if (minute < 10) {
            String.format(
                Locale.getDefault(),
                context.getString(R.string.zero_label),
                minute
            )
        } else {
            String.format(Locale.getDefault(), "%d", minute)
        }
        return "$hourString:$minuteString"
    }

    /**
     * Get animation file according to weather status code
     *
     * @param weatherCode int weather status code
     * @return id of animation json file
     */
    fun getWeatherAnimation(weatherCode: Int): Int {
        if (weatherCode / 100 == 2) {
            return R.raw.storm_weather
        } else if (weatherCode / 100 == 3) {
            return R.raw.rainy_weather
        } else if (weatherCode / 100 == 5) {
            return R.raw.rainy_weather
        } else if (weatherCode / 100 == 6) {
            return R.raw.snow_weather
        } else if (weatherCode / 100 == 7) {
            return R.raw.unknown
        } else if (weatherCode == 800) {
            return R.raw.clear_day
        } else if (weatherCode == 801) {
            return R.raw.few_clouds
        } else if (weatherCode == 803) {
            return R.raw.broken_clouds
        } else if (weatherCode / 100 == 8) {
            return R.raw.cloudy_weather
        }
        return R.raw.unknown
    }

    /**
     * Get weather status string according to weather status code
     *
     * @param weatherCode weather status code
     * @param isRTL       boolean value
     * @return String weather status
     */
    fun getWeatherStatus(weatherCode: Int, isRTL: Boolean): String {
        if (weatherCode / 100 == 2) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[0]
            } else {
                Constants.WEATHER_STATUS[0]
            }
        } else if (weatherCode / 100 == 3) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[1]
            } else {
                Constants.WEATHER_STATUS[1]
            }
        } else if (weatherCode / 100 == 5) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[2]
            } else {
                Constants.WEATHER_STATUS[2]
            }
        } else if (weatherCode / 100 == 6) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[3]
            } else {
                Constants.WEATHER_STATUS[3]
            }
        } else if (weatherCode / 100 == 7) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[4]
            } else {
                Constants.WEATHER_STATUS[4]
            }
        } else if (weatherCode == 800) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[5]
            } else {
                Constants.WEATHER_STATUS[5]
            }
        } else if (weatherCode == 801) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[6]
            } else {
                Constants.WEATHER_STATUS[6]
            }
        } else if (weatherCode == 803) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[7]
            } else {
                Constants.WEATHER_STATUS[7]
            }
        } else if (weatherCode / 100 == 8) {
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN[8]
            } else {
                Constants.WEATHER_STATUS[8]
            }
        }
        return if (isRTL) {
            Constants.WEATHER_STATUS_PERSIAN[4]
        } else {
            Constants.WEATHER_STATUS[4]
        }
    }

    /**
     * Trim string text
     *
     * @param charSequence String text
     * @return String text
     */
    private fun trim(charSequence: CharSequence): CharSequence {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence
        }
        var end = charSequence.length - 1
        while (Character.isWhitespace(charSequence[end])) {
            end--
        }
        return charSequence.subSequence(0, end + 1)
    }

    /**
     * Check current direction of application. if is RTL return true
     *
     * @param context instance of [Context]
     * @return boolean value
     */
    fun isRTL(context: Context): Boolean {
        val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
        val directionality = Character.getDirectionality(locale.displayName[0]).toInt()
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
    }

    /**
     * Network status functions.
     */
    @SuppressLint("StaticFieldLeak")
    private var sApplication: Application? = null
    private fun init(app: Application?) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = applicationByReflect
            } else {
                sApplication = app
            }
        } else {
            if (app != null && app.javaClass != sApplication!!.javaClass) {
                sApplication = app
            }
        }
    }

    val app: Application?
        get() {
            if (sApplication != null) return sApplication
            val app = applicationByReflect
            init(app)
            return app
        }
    private val applicationByReflect: Application
        private get() {
            try {
                @SuppressLint("PrivateApi") val activityThread =
                    Class.forName("android.app.ActivityThread")
                val thread = activityThread.getMethod("currentActivityThread").invoke(null)
                val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
                return app as Application
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            throw NullPointerException("u should init first")
        }

    /**
     * If network connection is connect, return true
     *
     * @return boolean value
     */
    @get:RequiresPermission(permission.ACCESS_NETWORK_STATE)
    val isNetworkConnected: Boolean
        get() {
            val info = activeNetworkInfo
            return info != null && info.isConnected
        }

    /**
     * Get activity network info instace
     *
     * @return instance of [NetworkInfo]
     */
    @get:RequiresPermission(permission.ACCESS_NETWORK_STATE)
    private val activeNetworkInfo: NetworkInfo?
        private get() {
            val cm = app!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return null
            return cm.activeNetworkInfo
        }

}