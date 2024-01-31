package com.doreamon.treasure.base

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.GET_META_DATA
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoKit
import com.doreamon.treasure.BuildConfig
import com.doreamon.treasure.utils.AppManager
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.coroutines.delay
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author wzh
 * @date 2021/11/8
 */
internal class App : Application() {


    val delegates: List<ApplicationDelegate> by lazy {
        findApplicationDelegate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        if (delegates.isNotEmpty()) {
            for (delegate in delegates) {
                delegate.attachBaseContext(this, base)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppManager.register(this)

        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
        }

        if (delegates.isNotEmpty()) {
            for (delegate in delegates) {
                delegate.onCreate(this)
            }
        }

        //doKit初始化
        DoKit.Builder(this)
//            .productId("需要使用平台功能的话，需要到dokit.cn平台申请id")
            .build()


        //bugly初始化
        CrashReport.initCrashReport(this, "31a286166b", true)


    }


    private fun findApplicationDelegate(): List<ApplicationDelegate> {
        val delegates: ArrayList<ApplicationDelegate> = ArrayList()
        val info = packageManager.getApplicationInfo(packageName, GET_META_DATA)
        if (info.metaData != null) {
            for (key in info.metaData.keySet()) {
                val value = info.metaData.get(key)

                if (MODULE_META_KEY == value) {

                    val delegate = initApplicationDelegate(key)
                    delegate.let { delegates.add(it) }
                }

            }

        }
        return delegates
    }


    private fun initApplicationDelegate(className: String): ApplicationDelegate {
        val clazz: Class<*>
        val instance: Any
        try {
            clazz = Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("找不到$className", e)
        }


        try {
            instance = clazz.newInstance()
        } catch (e: Exception) {
            throw RuntimeException("不能获取${className}的实例", e)
        }

        if (instance !is ApplicationDelegate) {
            throw RuntimeException("不能获取${ApplicationDelegate::class.java.name}的实例$instance")
        }


        return instance
    }


    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }

}