package com.danmealon.smack.Tests

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.danmealon.smack.Controller.LoginActivity
import com.danmealon.smack.Screens.LoginScreen
import com.danmealon.smack.Screens.MainScreen
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)

class ChatTest {

//    @Rule
//
//    var activityTestRule = ActivityTestRule<LoginActivity>(
//        LoginActivity::class.java)

    @Test
    fun testLoginScreen(){
        val loginScreen = LoginScreen() //we create an object of LoginScreen class
        loginScreen.enterEmail()
        loginScreen.enterPassword()
        loginScreen.clickOnLoginBtn()

        val mainScreen = MainScreen()
        mainScreen.clickOnChannelBtn()
        mainScreen.enterChannelName()
        mainScreen.enterChannelDescription()
        mainScreen.clickOnAddBtn()
        mainScreen

    }
}