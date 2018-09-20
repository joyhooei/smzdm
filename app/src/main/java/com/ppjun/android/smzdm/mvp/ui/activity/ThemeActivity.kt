package com.ppjun.android.smzdm.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.main.ColorTheme
import com.ppjun.android.smzdm.mvp.ui.adapter.ThemeAdapter
import kotlinx.android.synthetic.main.activity_choose_color.*
import kotlinx.android.synthetic.main.include_title.*

class ThemeActivity : BaseUI<IPresenter>() {
    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        intent.putExtra(Constant.IS_INIT_TOOLBAR, false)

        return R.layout.activity_choose_color
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolSearch.visibility= View.GONE
        themeRv.layoutManager = GridLayoutManager(ThemeActivity@ this, 5)
        val list = ArrayList<ColorTheme>()
        for(i in 1..10){
            list.add(ColorTheme("1",R.color.colorAccent))
        }
        themeRv.adapter = ThemeAdapter(list)
    }

    override fun onResume() {
        super.onResume()
        toolbarTitle.setText(R.string.theme_color)

    }
}