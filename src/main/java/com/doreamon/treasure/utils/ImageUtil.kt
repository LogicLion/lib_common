package com.doreamon.treasure.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author wzh
 * @date 2022/5/11
 */
object ImageUtil {

    fun loadUrl(iv: ImageView, url: String?) {
        Glide.with(iv.context).load(url).into(iv)
    }

}