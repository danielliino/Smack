package com.danmealon.smack.Screens

import android.support.test.espresso.ViewInteraction

abstract class BaseScreen {

    //We use this abstract class to make sure that whenever rwe create an instance of the screen we are on a right place
    abstract val uniqueView: ViewInteraction
}