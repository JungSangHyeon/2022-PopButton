package com.example.popbutton

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {

    companion object{
        val popRatio = 0.1f
        val popCount = 8
        val maxDistanceRatio = 0.4f
        val animationPlayTime = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopButton(
                drawableId = R.drawable.ic_baseline_thumb_up_24,
                modifier = Modifier.fillMaxSize(),
                onClick = {}
            )
        }
    }

    @Composable
    fun PopButton(
        drawableId: Int,
        modifier: Modifier = Modifier,
        onClick: ()->Unit
    ) {
        val context = LocalContext.current
        val buttonSize = remember{ mutableStateOf<Int?>(null) }
        val bitmapDrawable = remember{
            derivedStateOf {
                buttonSize.value?.let {
                    getBitmapFromDrawable(
                        size =  (it * popRatio).toInt(),
                        context = context,
                        drawableId = drawableId
                    ).asImageBitmap()
                }
             }
        }
        val clicked = remember {
            mutableStateOf(false)
        }

        val animationValue = animateFloatAsState(
            targetValue = if(clicked.value) 1f else 0f,
            animationSpec = tween(durationMillis = animationPlayTime),
            finishedListener = {
                clicked.value = false
            }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onGloballyPositioned {
                    buttonSize.value = it.size.width
                }
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                bitmapDrawable.value?.let { bitmap ->
                    buttonSize.value?.let { size ->
                        val popSize = size * popRatio
                        val angle = 360/popCount
                        val diff = size * maxDistanceRatio * animationValue.value
                        (0 until popCount).forEach{
                            val radianAngle = Math.toRadians((angle * it).toDouble())
                            val x = diff * cos(radianAngle).toFloat()
                            val y = diff * sin(radianAngle).toFloat()
                            drawImage(
                                topLeft = Offset(
                                    this.center.x + x - popSize/2,
                                    this.center.y + y - popSize/2
                                ),
                                image = bitmap,
                                alpha = animationValue.value,
                            )
                        }
                    }
                }
            }
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .padding(96.dp)
                    .clickable(
                        interactionSource = remember{ MutableInteractionSource() },
                        indication = null
                    ) {
                        onClick()
                        clicked.value = true
                    }
            )
        }

    }
}

fun getBitmapFromDrawable(
    size: Int,
    context: Context,
    drawableId: Int
): Bitmap {
    val drawable = context.getDrawable(drawableId)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    drawable!!.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}