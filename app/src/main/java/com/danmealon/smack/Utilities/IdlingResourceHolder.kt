package com.danmealon.smack.Utilities

import android.support.test.espresso.idling.CountingIdlingResource

object IdlingResourceHolder {

    val idlingResource = CountingIdlingResource("Network Idling Resource", true)
}