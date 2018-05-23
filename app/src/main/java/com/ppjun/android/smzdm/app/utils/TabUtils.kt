package com.ppjun.android.smzdm.app.utils

import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.widget.LinearLayout
import java.lang.reflect.Field

class TabUtils {

    companion object {
        fun setIndicator(tabs: TabLayout, leftDip: Int, rightDip: Int) {

            val tabLayout = tabs.javaClass
            lateinit var tabStrip: Field
            try {
                tabStrip = tabLayout.getDeclaredField("mTabStrip")
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }

            tabStrip.isAccessible = true
            var llTab: LinearLayout? = null
            try {
                llTab = tabStrip.get(tabs) as LinearLayout
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, leftDip.toFloat(), Resources.getSystem().displayMetrics).toInt()
            val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, rightDip.toFloat(), Resources.getSystem().displayMetrics).toInt()

            for (i in 0 until requireNotNull(llTab).childCount) {
                val child = requireNotNull(llTab).getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                params.leftMargin = left
                params.rightMargin = right
                child.layoutParams = params
                child.invalidate()
            }


        }
    }
}