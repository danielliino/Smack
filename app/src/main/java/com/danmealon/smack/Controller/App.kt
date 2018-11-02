package com.danmealon.smack.Controller

import android.app.Application
import com.danmealon.smack.Utilities.SharedPrefs

class App : Application(){

    //to initialize shared preferences we need companion object (it is kind of singleton but for inside of specific class, so we are allowed to create only one instance of sharedPreferences ):

    companion object {
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        //we initializing shared preferences here
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}