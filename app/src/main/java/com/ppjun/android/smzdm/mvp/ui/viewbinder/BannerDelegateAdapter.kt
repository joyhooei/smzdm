package com.ppjun.android.smzdm.mvp.ui.viewbinder

import android.content.Intent
import android.os.Build
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.ppbannerview.PPBannerView
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.main.MainBanner
import com.ppjun.android.smzdm.mvp.ui.activity.ArticleInfoActivity
import com.ppjun.android.smzdm.mvp.ui.activity.PriceInfoActivity
import com.ppjun.android.smzdm.mvp.ui.activity.WebActivity
import kotlinx.android.synthetic.main.include_banner.view.*


class BannerDelegateAdapter(var data:List<MainBanner>):DelegateAdapter.Adapter<BannerDelegateAdapter.BannerHolder>() {
    override fun getItemCount(): Int {
        return 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        return BannerHolder(LayoutInflater.from(parent.context).inflate(R.layout.include_banner, parent, false))
    }



    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {

        val context = holder.itemView.context
        val datas = ArrayList<String>()
        for (image in data) {
            datas.add(image.img)

        }
        holder.banner.setBannerData(datas)
        holder.banner.mOnBannerSwitchListener = object : PPBannerView.OnBannerSwitchListener {
            override fun onSwitch(position: Int, imageView: AppCompatImageView) {

                val typedValue = TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
                val attribute = intArrayOf(android.R.attr.selectableItemBackground)
                val typedArray = context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageView.foreground=typedArray.getDrawable(0)
                }

                ArmsUtils.obtainAppComponentFromContext(context)
                        .imageLoader()
                        .loadImage(context, ImageConfigImpl.builder()
                                .url(datas[position])
                                .imageView(imageView)
                                .build())
                imageView.setOnClickListener {
                    when (data[position].redirectData.linkType) {
                        "other" -> {
                            var targetUrl = data[position].link.split("?")[1].split("&")[0]
                            LogUtils.debugInfo("debug=", targetUrl)
                            val result = Intent(context, WebActivity::class.java)
                            result.putExtra(Constant.URL, "http://$targetUrl")
                            context.startActivity(result)

                        }
                        "yuanchuang" -> {
                            val resultIntent = Intent(context, ArticleInfoActivity::class.java)
                            resultIntent.putExtra(Constant.ID, data[position].redirectData.linkVal)
                            resultIntent.putExtra(Constant.COLLECT, "0")
                            resultIntent.putExtra(Constant.COMMENT, "0")
                            context.startActivity(resultIntent)
                        }
                        "youhui", "haitao" -> {
                            val resultIntent = Intent(context, PriceInfoActivity::class.java)
                            resultIntent.putExtra(Constant.ID,data[position].redirectData.linkVal)
                            context.startActivity(resultIntent)

                        }
                        else -> {

                        }
                    }
                }

            }

        }


    }


    class BannerHolder( view: android.view.View) : RecyclerView.ViewHolder(view){
        val banner = view.includeBanner!!

    }
}