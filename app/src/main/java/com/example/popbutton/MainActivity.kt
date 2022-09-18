package com.example.popbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopButton(
                modifier = Modifier.size(200.dp, 200.dp),

                drawableId = R.drawable.ic_outline_thumb_up_alt_24,
                centerImageSize = 100.dp,

                popEffect = PopEffect(
                    context = this@MainActivity,
                    drawableId = R.drawable.ic_baseline_favorite_24,
                    color = Color.Red
                ),

                onClick = {},
            )
        }
    }
}