package com.example.popbutton

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun PopButton(
    modifier: Modifier = Modifier,
    drawableId: Int,
    centerImageSize: Dp,
    popEffect: PopEffect,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.onGloballyPositioned {
            popEffect.initTargetSize(it.size.width)
        }
    ) {
        popEffect.popCanvas(
            Modifier.matchParentSize()
        )

        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier
                .size(centerImageSize)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    popEffect.startEffect()
                    onClick()
                }
        )
    }
}