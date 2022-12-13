package com.example.weatherapp.core.data.utils

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T> (
    var code: Int,
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("errors") var errors : List<String>? = null,
    @SerializedName("data") var data : List<T>? = null
)

data class WrappedResponse<T> (
    var code: Int,
    @SerializedName("status") var status : StatusBase,
    @SerializedName("error") var error : String,
    @SerializedName("data") var data : T? = null
)

data class StatusBase(
    @SerializedName("status") val status: Boolean,
    @SerializedName("code") var code: String,
    @SerializedName("message")  val message: String,

)