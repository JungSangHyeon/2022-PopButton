package com.example.popbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val isChecked = mutableStateOf(false)
    private val popButtonDrawableId = derivedStateOf {
        if (isChecked.value) R.drawable.ic_baseline_thumb_up_24
        else R.drawable.ic_outline_thumb_up_alt_24
    }
    private val popButtonEffectDrawableId = derivedStateOf {
        if (isChecked.value) R.drawable.ic_baseline_favorite_24
        else R.drawable.ic_baseline_heart_broken_24
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                PopButton(
                    modifier = Modifier.size(200.dp, 200.dp),

                    drawableId = popButtonDrawableId,
                    centerImageSize = 100.dp,

                    popEffect = PopEffect(
                        context = this@MainActivity,
                        drawableId = popButtonEffectDrawableId,
                        color = Color.Red
                    ),

                    onClick = {
                        isChecked.value = !isChecked.value
                    }
                )
            }
        }
    }
}