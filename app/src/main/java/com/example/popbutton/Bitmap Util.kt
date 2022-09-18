package com.example.popbutton

import android.content.Context
import android.graphics.Bitmap

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