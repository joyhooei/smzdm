package com.ppjun.android.smzdm.mvp.ui.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.utils.AutoLayoutHelper

class AutoCardView : CardView {
    var mHelper:AutoLayoutHelper= AutoLayoutHelper(this)
    constructor(context: Context) : super(context)

    constructor(context: Context,attrs:AttributeSet):super(context,attrs)


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return AutoFrameLayout.LayoutParams(context,attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(!isInEditMode){
            mHelper.adjustChildren()
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}