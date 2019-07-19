package dan.wilkie.common.view

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}