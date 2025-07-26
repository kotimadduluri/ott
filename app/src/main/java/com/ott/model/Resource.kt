package com.ott.model

import com.ott.core_ui.util.UiText


sealed class Resource(
    val message: UiText
) {

    class Success<T>(val data: T?) :
        Resource(message = UiText.PlainString("Success"))

    class Error(
        message : UiText = UiText.PlainString("Error"),
        error: Throwable? = null
    ) :
        Resource(message = message)
}

fun <T> Resource.asSuccess() : Resource.Success<T>{
    return this as Resource.Success<T>
}

fun Resource.asError(): Resource.Error {
    return this as Resource.Error
}