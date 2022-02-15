package com.jibin.codeTest

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object{
        const val PREFS_NAME = "EmpPref"
        const val PREF_VERSION_CODE_KEY = "version_code"
        const val DOESNT_EXIST = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkFirstRun()
    }

    private fun isOnline(context: Context): Boolean {
        val conMgr =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo

        if (netInfo == null || !netInfo.isConnected || !netInfo.isAvailable) {

            try {
                val intent = Intent(Intent.ACTION_MAIN, null)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                val cn =
                    ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings")
                intent.component = cn
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (ignored: ActivityNotFoundException) {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
            Toast.makeText(context, "Please turn on wifi/mobile data", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun checkFirstRun() {


        val currentVersionCode = BuildConfig.VERSION_CODE

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)

        when {
            currentVersionCode == savedVersionCode -> {
                return
            }
            savedVersionCode == DOESNT_EXIST -> {

                isOnline(this)
            }
            currentVersionCode > savedVersionCode -> {

            }
        }
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
    }
}