package com.doreamon.treasure.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.doreamon.treasure.base.App

/**
 * @author wzh
 * @date 2021/12/27
 */
/**
 * 根据资源id[resId] 获取字符串[String]
 */
@JvmOverloads
fun getStringById(@StringRes resId: Int, context: Context = App.instance): String {
    return context.getString(resId)
}


/**
 * 根据资源id[colorResId] 获取颜色值[Int]
 * > [context] 可选，默认[AppManager.getContext]
 */
@ColorInt
@JvmOverloads
fun getColorById(@ColorRes colorResId: Int, context: Context = AppManager.getContext()): Int {
    return ContextCompat.getColor(context, colorResId)
}