package com.danmealon.smack.Tests

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.danmealon.smack.Controller.LoginActivity
import com.danmealon.smack.Controller.MainActivity
import com.danmealon.smack.R
import com.danmealon.smack.Screens.LoginScreen
import com.danmealon.smack.Screens.MainScreen
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class ChatTest {

    @get:Rule//Rule means what screen should we start from
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun addANewChannel(){

        onView(withContentDescription("Open navigation drawer"))//If there is no Id or text use content description.

            .perform(click())
        onView(withId(R.id.loginBtnNavHeader))
            .perform(click())
        val loginScreen = LoginScreen() //we create an object of LoginScreen class

        loginScreen.enterEmail()
        loginScreen.enterPassword()
        //loginScreen.clickOnLoginBtn()
        sleep(3000)
        val mainScreen = loginScreen.clickOnLoginBtn() //as we tap on LoginBtn we return Main Screen
        mainScreen.clickOnChannelBtn()
        mainScreen.enterChannelName()
        mainScreen.enterChannelDescription()
        mainScreen.clickOnAddBtn()
        sleep(2000)
        mainScreen.channelDisplayed()


    }
}