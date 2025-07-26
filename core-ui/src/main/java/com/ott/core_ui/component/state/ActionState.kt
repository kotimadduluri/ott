package com.ott.core_ui.component.state

import com.common.util.UiImage
import com.ott.core_ui.R
import com.ott.core_ui.util.UiText


sealed class ActionState(
    val title: UiText,
    val message: UiText,
    val icon: UiImage
) {
    class LOADING(
        title: UiText = UiText.PlainString("Loading..."),
        message: UiText = UiText.PlainString("Please wait"),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_wifi_off)
    ) : ActionState(title, message, icon)

    class ERROR(
        title: UiText = UiText.PlainString("Error"),
        message: UiText = UiText.PlainString("Something went wrong"),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_error)
    ) : ActionState(title, message, icon)

    class SUCCESS(
        title: UiText = UiText.PlainString("Success"),
        message: UiText = UiText.PlainString("Completed successfully"),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_success)
    ) : ActionState(title, message, icon)

    class WARNING(
        title: UiText = UiText.PlainString("Warning"),
        message: UiText = UiText.PlainString("Action may have issues"),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_warning)
    ) : ActionState(title, message, icon)
}