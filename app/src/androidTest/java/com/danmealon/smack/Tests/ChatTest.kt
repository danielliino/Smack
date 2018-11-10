package com.danmealon.smack.Tests

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.danmealon.smack.Screens.LoginScreen
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)

class ChatTest {
    @Test
    fun testLoginScreen(){
        val loginScreen = LoginScreen() //we create an object of LoginScreen class
        loginScreen.enterEmail()
        loginScreen.enterPassword()
        loginScreen.clickOnLoginBtn()

    }
}