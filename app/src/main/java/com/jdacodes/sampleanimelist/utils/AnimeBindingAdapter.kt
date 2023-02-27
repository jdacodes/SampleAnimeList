package com.jdacodes.sampleanimelist.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jdacodes.sampleanimelist.model.*

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
fun setStudios(textView: TextView, items: List<Studio>?) {
    textView.text = items?.joinToString { it.name.toString() } ?: ""
}

@BindingAdapter("producers")
fun setProducers(textView: TextView, items: List<Producer>?) {
    textView.text = items?.joinToString { it.name.toString() } ?: ""
}

@BindingAdapter("licensors")
fun setLicensors(textView: TextView, items: List<Licensor>?) {
    textView.text = items?.joinToString { it.name.toString() } ?: ""
}

@BindingAdapter("genres")
fun setGenres(textView: TextView, items: List<Genre>) {
    val stringBuilder: StringBuilder = StringBuilder().apply {
        append("Genres: ")
            .append(items.joinToString { it.name.toString() })
    }
    textView.text = stringBuilder
}

@BindingAdapter(
    "showAlertDialog", "alertDialogTitle", "positiveButtonText",
    requireAll = false
)
fun showAlertDialog(
    view: View,
    text: String? = null,
    title: String? = null,
    positiveButtonText: String? = null
) {
    view.setOnClickListener { v ->
        val builder = MaterialAlertDialogBuilder(view.context)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton(positiveButtonText, null)
        builder.show()
    }
}


@BindingAdapter("addChips")
fun ChipGroup.setGenres(genres: List<Genre>?) {
    // Remove any existing chips from the group
    removeAllViews()

    // Add a chip for each genre in the list
    genres?.forEach { genre ->
        val chip = Chip(context)
        chip.text = genre.name


        addView(chip)
    }
}

@BindingAdapter("addChipsDemographic")
fun ChipGroup.setDemographic(demographic: List<Demographic>?) {
    // Remove any existing chips from the group
    removeAllViews()

    // Add a chip for each genre in the list
    demographic?.forEach { demographic ->
        val chip = Chip(context)
        chip.text = demographic.name


        addView(chip)
    }
}

@BindingAdapter("addChipsTheme")
fun ChipGroup.setThemes(theme: List<Theme>?) {
    // Remove any existing chips from the group
    removeAllViews()

    // Add a chip for each genre in the list
    theme?.forEach { theme ->
        val chip = Chip(context)
        chip.text = theme.name


        addView(chip)
    }
}
//@BindingAdapter("isGone")
//fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
//    if (isGone == null || isGone) {
//        view.hide()
//    } else {
//        view.show()
//    }
//}
