package com.ott.util

fun String?.orDefault(default: String = ""): String {
    return this ?: default
}