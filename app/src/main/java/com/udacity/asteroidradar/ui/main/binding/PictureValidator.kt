package com.udacity.asteroidradar.ui.main.binding

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.udacity.asteroidradar.R

object PictureValidator {

    fun getImageToShow(context: Context, url: String): Any? {
        return if (isValid(url)) url
        else getDefaultImage(context)
    }

    private fun isValid(url: String): Boolean {
        return ValidImageFormat.values()
            .any { url.endsWith(it.extension) }
    }

    private fun getDefaultImage(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.image_of_the_day_default)
    }

}

enum class ValidImageFormat(val extension: String) {
    JPG("jpg"),
    JPEG("jpeg"),
    GIF("gif"),
    PNG("png")
}