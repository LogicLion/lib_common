package com.doreamon.treasure.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

/**
 * @author wzh
 * @date 2022/5/11
 */
object ImageUtil {

    fun loadUrl(iv: ImageView, url: String?) {
        Glide.with(iv.context).load(url).into(iv)
    }

    fun loadDrawable(iv: ImageView, @DrawableRes resourceId: Int?) {
        Glide.with(iv.context).load(resourceId).into(iv)
    }


}