package com.ppjun.android.smzdm.wxapi

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.ppjun.android.smzdm.R
import kotlinx.android.synthetic.main.to_top.view.*

class Apple<T>() {

    init {
        val listT=ArrayList<T>()//设置泛型的Arraylist,可以传入String ,int ,自定义类.当然也可以传入Any
        val listAny=ArrayList<Any>()// 相当于java的Object，也可以传入String ,int 。当这个Apple类没有T， 你又想传入不同类型的类，就用到Any
        val listObject=ArrayList<Object>()//suggest Any instead of object，用 Any 代替,相当于 Any。最后编译器会将Any 和object编译成class java.lang.Object
        val listStar=ArrayList<DelegateAdapter.Adapter<*>>()//这里的*号表示viewholder的子类，extends关系只能用* 而不能用Any T

    }

    companion object {
        fun appleLog(){
            Log.i("debug=","appleLog")
        }
        internal fun appleInternalLog(){
            Log.i("debug=","appleInternalLog")
        }
    }



}