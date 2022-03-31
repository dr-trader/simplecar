package ru.donspb.simplecar

import android.graphics.drawable.Drawable

data class CarModel(
    val name: String,
    val width: Int,
    val height: Int,
    val modelImg: Drawable?
)
