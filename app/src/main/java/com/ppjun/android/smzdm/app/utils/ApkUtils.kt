package com.ppjun.android.smzdm.app.utils

import android.content.Context
import java.io.File

class ApkUtils{

    companion object {

        fun isInstall(packageName:String):Boolean{

            return File("data/data/$packageName").exists()
        }
        fun launchApp(context: Context, packageName:String){
            val intent=context.packageManager.getLaunchIntentForPackage(packageName)
            context.startActivity(intent)
        }

    }
}