package com.ott.core_ui.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class PlainString(val value:String) : UiText()
    class StringResource(
        @StringRes val id:Int,
        val args:Array<Any> = emptyArray()
    ) : UiText()

    @Composable
    fun asString():String{
        return when(this){
            is PlainString -> value
            is StringResource -> stringResource(id,args)
        }
    }
}