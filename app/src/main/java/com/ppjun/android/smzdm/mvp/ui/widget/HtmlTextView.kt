package com.ppjun.android.smzdm.mvp.ui.widget

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.widget.TextView
import java.io.InputStream

class HtmlTextView(context: Context, attr: AttributeSet) : TextView(context, attr) {

    init {

    }

    fun convertStreamToString(input: InputStream): String {
        val s = java.util.Scanner(input).useDelimiter("\\A")

        return if (s.hasNext()) s.next() else ""
    }




}