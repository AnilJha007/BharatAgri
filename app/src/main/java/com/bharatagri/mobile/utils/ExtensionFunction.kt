package com.bharatagri.mobile.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bharatagri.mobile.R
import com.bharatagri.mobile.utils.Constants.IMAGE_BASE_PATH
import com.bharatagri.mobile.utils.Constants.IMAGE_SIZE_185
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, text, duration).apply { show() }
}

fun ImageView.setImage(imageEndPoint: String, imageSize: String = IMAGE_SIZE_185) {
    val imageUrl = IMAGE_BASE_PATH + imageSize + imageEndPoint
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.placeholder)
        .dontAnimate()
        .into(this)
}

fun TextView.setTextOrHide(text: String?) {
    text?.let {
        setText(text)
        show()
    } ?: hide()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}