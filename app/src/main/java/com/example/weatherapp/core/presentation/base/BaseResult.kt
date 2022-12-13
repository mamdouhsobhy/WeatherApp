package com.example.weatherapp.core.presentation.base

sealed class BaseResult <out T> {
    data class DataState<T: Any>( val items: T?): BaseResult<T>()
    data class ErrorState(val errorCode: Int, val errorMessage: String): BaseResult<Nothing>()
}