package com.example.screen_lake.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

object Extensions {

    fun Modifier.noRippleClickable(isClickable:Boolean=true,onClick: () -> Unit): Modifier = composed {
        clickable(enabled = isClickable,indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }
}