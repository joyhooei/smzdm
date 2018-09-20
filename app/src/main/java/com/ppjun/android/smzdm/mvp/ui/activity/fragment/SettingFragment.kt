package com.ppjun.android.smzdm.mvp.ui.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.ui.activity.ThemeActivity
import kotlinx.android.synthetic.main.view_setting.view.*

class SettingFragment : BaseFragment<IPresenter>() {
    override fun setupFragmentComponent(appComponent: AppComponent) {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.view_setting, container, false)
        initView(view)
        return view
    }

    fun initView(view: View) {
        val colorRv = view.colorLayout
        colorRv.setOnClickListener {
            startActivity(Intent(activity, ThemeActivity::class.java))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun setData(data: Any?) {

    }
}