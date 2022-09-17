package com.example.popbutton

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {

    companion object{
        val popRatio = 0.1f
        val popCount = 8
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopButton(
                drawableId = R.drawable.ic_baseline_bakery_dining_24,
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
                        size = (it * popRatio).toInt(),
                        context = context,
                        drawableId = drawableId
                    ).asImageBitmap()
                }
             }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .clickable { onClick() }
                    .onGloballyPositioned {
                        buttonSize.value = it.size.width
                    }
            )
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                bitmapDrawable.value?.let {
                    drawImage(it)
                }
            }
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