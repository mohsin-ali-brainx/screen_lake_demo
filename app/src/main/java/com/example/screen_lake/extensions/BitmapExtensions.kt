package com.example.screen_lake.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun Context.getAppIconBitmap(appPackageName:String): ImageBitmap? {
    return try {
        packageManager.getApplicationIcon(appPackageName).drawableToBitmap()?.asImageBitmap()
    } catch (e: Exception) {
        // Handle any exceptions that might occur during drawable retrieval
        null
    }
}

fun Drawable.drawableToBitmap(): Bitmap? {
    if (this is BitmapDrawable) {
        return bitmap
    }

    if (!(intrinsicWidth > 0 && intrinsicHeight > 0)) {
        return null
    }

    val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bitmap
}