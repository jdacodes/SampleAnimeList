package com.jdacodes.sampleanimelist.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jdacodes.sampleanimelist.model.Genre
import com.jdacodes.sampleanimelist.model.Studio

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("studios")
fun setStudios(textView: TextView, items: List<Studio>) {
    textView.text = items.joinToString { it.name.toString() }
}

@BindingAdapter("genres")
fun setGenres(textView: TextView, items: List<Genre>) {
    var stringBuilder = StringBuilder()
    stringBuilder.append("Genres: ")
        .append(items.joinToString { it.name.toString() })
    textView.text = stringBuilder
}
//@BindingAdapter("isGone")
//fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
//    if (isGone == null || isGone) {
//        view.hide()
//    } else {
//        view.show()
//    }
//}