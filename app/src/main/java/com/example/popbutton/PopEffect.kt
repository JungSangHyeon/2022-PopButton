package com.example.popbutton

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import kotlin.math.cos
import kotlin.math.sin

class PopEffect(
    context: Context,
    drawableId: State<Int>,
    private val color: Color = Color.Black,
    private val popSizeRatio: Float = 0.15f, // targetSize * popSizeRatio = popSize
    private val popCount: Int = 8,
    private val maxDistanceRatio: Float = 0.43f, // targetSize * maxDistanceRatio = popMoveDistance
    private val animationPlayTime: Int = 300, // ms
    private val startDistanceRatio: Float = 0.6f // popMoveDistance * startDistanceRatio 지점 부터 효과 시작
) {
    private val targetSize = mutableStateOf<Int?>(null)
    private val bitmapDrawable = derivedStateOf {
        targetSize.value?.let {
            getBitmapFromDrawable(
                size = (it * popSizeRatio).toInt(),
                context = context,
                drawableId = drawableId.value
            ).asImageBitmap()
        }
    }
    private val runEffect = mutableStateOf(false)

    fun initTargetSize(size: Int) { targetSize.value = size }
    fun startEffect() { runEffect.value = true }

    @Composable
    fun popCanvas(
        modifier: Modifier = Modifier
    ) {
        val animationValue = animateFloatAsState(
            targetValue = if (runEffect.value) 1f else startDistanceRatio,
            animationSpec = tween(durationMillis = animationPlayTime),
            finishedListener = { runEffect.value = false }
        )

        Canvas(
            modifier = modifier
        ) {
            bitmapDrawable.value?.let { bitmap ->
                targetSize.value?.let { size ->
                    val popSize = size * popSizeRatio
                    val angle = 360 / popCount
                    val diff = size * maxDistanceRatio * animationValue.value
                    (0 until popCount).forEach {
                        val radianAngle = Math.toRadians((angle * it).toDouble())
                        val x = diff * cos(radianAngle).toFloat()
                        val y = diff * sin(radianAngle).toFloat()
                        drawImage(
                            topLeft = Offset(
                                this.center.x + x - popSize / 2,
                                this.center.y + y - popSize / 2
                            ),
                            image = bitmap,
                            alpha = (animationValue.value - startDistanceRatio) * (1 / (1 - startDistanceRatio)),
                            colorFilter = ColorFilter.tint(color)
                        )
                    }
                }
            }
        }
    }
}