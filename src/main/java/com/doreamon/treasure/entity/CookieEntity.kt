package com.doreamon.treasure.entity

import okhttp3.Cookie

/**
 * Cookie 数据实体类
 *
 * @param cookies Cookie 列表
 *
 * * 创建时间：2019/10/15
 *
 * @author 王杰
 */
data class CookieEntity(
        val cookies: ArrayList<Cookie>? = arrayListOf()
):java.io.Serializable