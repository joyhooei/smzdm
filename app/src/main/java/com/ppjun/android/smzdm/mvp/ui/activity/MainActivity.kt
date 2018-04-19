package com.ppjun.android.smzdm.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.utils.BottomNavigationHelper
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseUI<IPresenter>() {
    var currentFragment: Fragment? = null
    lateinit var mainFragment: Fragment
    var firstTimeStamp = 0L
    override fun initData(savedInstanceState: Bundle?) {
        mainFragment = MainFragment()
        BottomNavigationHelper.disableShiftModel(navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setBackInvisible()
        switchFragment(0)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications2 -> {
                switchFragment(0)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun switchFragment(position: Int) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (position) {
            0 -> {
                val mainTag = "mMainFragment"
                showFragment(transaction, mainFragment, mainTag)
            }
            1 -> {

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
                transaction.add(R.id.mainFrame, targetFragment, tag)
            } else {
                transaction.add(R.id.mainFrame, targetFragment, tag).hide(currentFragment)
            }

        }
        currentFragment = targetFragment
    }


    override fun onBackPressed() {

        if (System.currentTimeMillis() - firstTimeStamp > 2000) {
            toast("再按一次退出")

            firstTimeStamp = System.currentTimeMillis()
        } else {
            finish()
            System.exit(0)

        }
    }

    fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}
