package pt.amn.moveon.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun ImageView.loadDrawableImage(context: Context, view: View, drawableId: String) {

    Glide.with(view)
        .load(context.resources.getIdentifier(drawableId, "drawable", context.packageName))
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this@loadDrawableImage)
}

