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
import com.danmealon.smack.Screens.Credentials
import com.danmealon.smack.Screens.LoginScreen
import com.danmealon.smack.Screens.MainScreen
import com.danmealon.smack.Utilities.CORRECT_EMAIL
import com.danmealon.smack.Utilities.CORRECT_PASSWORD
import com.danmealon.smack.Utilities.INCORRECT_EMAIL
import com.danmealon.smack.Utilities.INCORRECT_PASSWORD
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
            .enterEmail(CORRECT_EMAIL)
            .enterPassword(CORRECT_PASSWORD)

        //as we tap on LoginBtn we return Main Screen
        val mainScreen = loginScreen.clickOnLoginBtn(Credentials.Valid) as MainScreen //casting
            mainScreen.clickOnChannelBtn()
                .enterChannelName()
                .enterChannelDescription()
                .clickOnAddBtn()
        //sleep(2000)
        mainScreen.channelDisplayed()
    }

    @Test
    fun enterWrongCredentials(){

        onView(withContentDescription("Open navigation drawer"))
            .perform(click())
        onView(withId(R.id.loginBtnNavHeader))
            .perform(click())

        val loginScreen = LoginScreen()
            .enterEmail(INCORRECT_EMAIL)
            .enterPassword(INCORRECT_PASSWORD)

        loginScreen.clickOnLoginBtn(Credentials.Invalid) as LoginScreen //casting
        loginScreen.checkToastIsShown(mActivityTestRule)

    }
}