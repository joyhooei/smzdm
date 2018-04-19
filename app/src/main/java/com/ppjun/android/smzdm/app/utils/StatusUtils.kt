package com.ppjun.android.smzdm.app.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class StatusUtils {

    companion object {
        @TargetApi(19)
        fun transparencyBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val window = activity.window
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        /*
     */
        /**
         * 修改状态栏颜色，支持4.4以上版本
         * @param activity
         * @param colorId
         *//*
    public static void setStatusBarColor(Activity activity,int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
//      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }*/

        /**
         * 设置状态栏黑色字体图标，
         * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
         *
         * @param activity
         * @return 1:MIUUI9+ 2:Flyme 3:android6.0
         */
        fun StatusBarLightMode(activity: Activity): Int {
            val result = 0

            MIUISetStatusBarLightMode(activity, activity.window, true)
            FlymeSetStatusBarLightMode(activity.window, true)
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            return result
        }

        /**
         * 已知系统类型时，设置状态栏黑色字体图标。
         * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
         *
         * @param activity
         * @param type     1:MIUUI 2:Flyme 3:android6.0
         */
        fun StatusBarLightMode(activity: Activity, type: Int) {
            if (type == 1) {
                MIUISetStatusBarLightMode(activity, activity.window, true)
            } else if (type == 2) {
                FlymeSetStatusBarLightMode(activity.window, true)
            } else if (type == 3) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

        }

        /**
         * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
         */
        fun StatusBarDarkMode(activity: Activity, type: Int) {
            if (type == 1) {
                MIUISetStatusBarLightMode(activity, activity.window, false)
            } else if (type == 2) {
                FlymeSetStatusBarLightMode(activity.window, false)
            } else if (type == 3) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }

        }


        /**
         * 设置状态栏图标为深色和魅族特定的文字风格
         * 可以用来判断是否为Flyme用户
         *
         * @param window 需要设置的窗口
         * @param dark   是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val lp = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java
                            .getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(lp)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(lp, value)
                    window.attributes = lp
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }

        /**
         * 设置状态栏字体图标为深色，需要MIUIV6以上
         *
         * @param window 需要设置的窗口
         * @param dark   是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        fun MIUISetStatusBarLightMode(activity: Activity, window: Window?, dark: Boolean): Boolean {
            var result = false


            if (Build.MANUFACTURER == "Xiaomi") {
                val versionName = getMiuiVersion()

                if (!TextUtils.isEmpty(versionName) && "V9" == versionName) {
                    StatusBarLightMode(activity, 3)
                    result = true
                    return result
                }


            }


            if (window != null) {
                val clazz = window.javaClass
                try {
                    var darkModeFlag = 0
                    @SuppressLint("PrivateApi") val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                    darkModeFlag = field.getInt(layoutParams)
                    val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                    }
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }

        fun getMiuiVersion(): String? {
            val propName = "ro.miui.ui.version.name"
            val line: String
            var input: BufferedReader? = null
            try {
                val p = Runtime.getRuntime().exec("getprop $propName")
                input = BufferedReader(
                        InputStreamReader(p.inputStream), 1024)
                line = input.readLine()
                input.close()
            } catch (ex: IOException) {

                return null
            } finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            return line
        }

        fun getStatusBarHeight(context: Context): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }


    }
}