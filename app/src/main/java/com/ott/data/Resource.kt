package com.ott.data

import com.ott.core_ui.util.UiText


sealed class Resource<out T> {
    data class Success<out T>(
        val data: T?,
        val message: UiText = UiText.PlainString("Success")
    ) : Resource<T>()

    data class Error(
        val message: UiText = UiText.PlainString("Something went wrong"),
        val throwable: Throwable? = null
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}

fun <T> Resource<T>.asSuccess(): Resource.Success<T> {
    return this as Resource.Success<T>
}

fun <T> Resource<T>.asError(): Resource.Error {
    return this as Resource.Error
}
