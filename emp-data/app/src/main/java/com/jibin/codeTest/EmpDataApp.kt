package com.jibin.codeTest

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class EmpDataApp: Application(){
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}