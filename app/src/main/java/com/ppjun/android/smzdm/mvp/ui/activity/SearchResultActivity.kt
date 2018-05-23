package com.ppjun.android.smzdm.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.LogUtils
import com.mancj.materialsearchbar.MaterialSearchBar
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant.Companion.KEYWORD
import com.ppjun.android.smzdm.app.utils.StatusUtils
import com.ppjun.android.smzdm.app.utils.TabUtils
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ArticleSearchFragment
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.MainFragment
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.PriceFragment
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.PriceSearchFragment
import kotlinx.android.synthetic.main.search_result_ui.*

class SearchResultActivity : BaseUI<IPresenter>() {
    private var currentFragment: Fragment? = null
    private lateinit var mainFragment: Fragment
    private lateinit var mArticleListFragment: Fragment
    private var currentPosition = 0
    private var mKeyword = ""
    private var isDifferentKeyWord = false

    override fun setupActivityComponent(appComponent: AppComponent?) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.search_result_ui
    }


    override fun initData(savedInstanceState: Bundle?) {

        mainFragment = PriceSearchFragment()
        mArticleListFragment = ArticleSearchFragment()
        val titles = arrayOf("好价", "好文")

        searchED.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
            }

            override fun onSearchStateChanged(enabled: Boolean) {
            }

            override fun onSearchConfirmed(text: CharSequence?) {

                LogUtils.debugInfo("word1=", text.toString())
                LogUtils.debugInfo("word2=", mKeyword)
                LogUtils.debugInfo("word3=", searchED.text.toString())
                isDifferentKeyWord = text.toString() != mKeyword


                mKeyword = searchED.text.toString()
                when (currentPosition) {
                    0 -> {
                        switchFragment(currentPosition)
                        mOnPriceSearchListener?.search(searchED.text.toString())
                        searchED.setPlaceHolder(searchED.text.toString())
                        searchED.setPlaceHolderColor(R.color.black)


                    }
                    1 -> {
                        switchFragment(currentPosition)
                        mOnArticleSearchListener?.search(searchED.text.toString())
                        searchED.setPlaceHolder(searchED.text.toString())
                        searchED.setPlaceHolderColor(R.color.black)

                    }
                }

            }

        })

        searchED.enableSearch()
        searchED.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0)
        searchTablayout
                .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {

                        LogUtils.debugInfo("position=", tab!!.position.toString())
                        currentPosition = tab!!.position
                        when (currentPosition) {

                            0 -> {
                                switchFragment(currentPosition)

                                    mOnPriceSearchListener?.search(searchED.text.toString())
                                    searchED.setPlaceHolder(searchED.text.toString())
                                    searchED.setPlaceHolderColor(R.color.black)

                            }
                            1 -> {
                                switchFragment(currentPosition)

                                    mOnArticleSearchListener?.search(searchED.text.toString())
                                    searchED.setPlaceHolder(searchED.text.toString())
                                    searchED.setPlaceHolderColor(R.color.black)


                            }
                        }

                    }

                })


        searchTablayout.addTab(searchTablayout.newTab().setText(titles[0]), true)
        searchTablayout.addTab(searchTablayout.newTab().setText(titles[1]))
        TabUtils.setIndicator(searchTablayout, 100, 100)

    }

    var mOnPriceSearchListener: OnPriceSearchListener? = null
    fun setOnPriceSearchListener(listener: OnPriceSearchListener) {
        this.mOnPriceSearchListener = listener
    }

    var mOnArticleSearchListener: OnArticleSearchListener? = null
    fun setOnArticleSearchListener(listener: OnArticleSearchListener) {
        this.mOnArticleSearchListener = listener
    }

    interface OnArticleSearchListener {
        fun search(keyword: String)
    }

    interface OnPriceSearchListener {
        fun search(keyword: String)
    }

    fun switchFragment(position: Int) {
        currentPosition = position
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (position) {
            0 -> {
                val mainTag = "mMainFragment"
                showFragment(transaction, mainFragment, mainTag)
            }
            1 -> {
                val priceTag = "mArticleListFragment"
                val bundle = Bundle()
                bundle.putString(KEYWORD, mKeyword)
                mArticleListFragment.arguments = bundle
                showFragment(transaction, mArticleListFragment, priceTag)

            }

        }
        transaction.setTransitionStyle(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()


    }

    fun showFragment(transaction: android.support.v4.app.FragmentTransaction, targetFragment: Fragment, tag: String) {

        if (targetFragment.isAdded) {
            if (currentFragment != targetFragment) {
                transaction.show(targetFragment).hide(currentFragment)
            }
        } else {
            if (currentFragment == null) {
                transaction.add(R.id.searchFrame, targetFragment, tag)
            } else {
                transaction.add(R.id.searchFrame, targetFragment, tag).hide(currentFragment)
            }

        }
        currentFragment = targetFragment
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && searchED.isSearchEnabled) {
            finish()
            return true
        }
        return super.dispatchKeyEvent(event)
    }

}