package com.ppjun.android.smzdm.app.utils

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView

class BottomNavigationHelper {


    companion object {
        @SuppressLint("RestrictedApi")
        fun disableShiftModel(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            val shiftModel = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftModel.isAccessible = true
            shiftModel.setBoolean(menuView, false)
            shiftModel.isAccessible = false
            for (i in 0..menuView.childCount) {
                val itemView :BottomNavigationItemView? = menuView.getChildAt(i) as BottomNavigationItemView?
                itemView?.setShiftingMode(false)
                itemView?.setChecked(itemView.itemData.isChecked)
            }
        }
    }
}