package com.danmealon.smack.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.danmealon.smack.R
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Thread.sleep

class LoginScreen : BaseScreen() {

    //OnView returns ViewInteraction class.
    //ViewInteraction. Provides the primary interface to perform actions or asserts on views.
    //Each interaction is associated with a view identified by a view matcher.
    //All view actions and asserts are performed on the UI thread
    //(ensuring sequential execution).

    private val email = "w@w.com"
    private val password = "123456"

    //type in correct email
    private val editTextEmail: ViewInteraction
    get() = onView(withId(R.id.loginEmailTxt))

    //type in correct password
    private val editTextPassword: ViewInteraction
        get() = onView(withId(R.id.loginPasswordText))

    //click on login
    private val loginBtn: ViewInteraction //the type of this variable is ViewInteraction
        get() = onView(withId(R.id.loginLoginBtn)) //we find the id of the btn

    //putting unique element to make sure we are on LoginScreen by using constructor
    override val uniqueView: ViewInteraction
        get() = loginBtn

    fun enterEmail():LoginScreen{
        editTextEmail.perform(typeText(email))
        return this
    }

    fun enterPassword():LoginScreen{
        editTextPassword.perform(typeText(password))
        return this
    }

    fun clickOnLoginBtn(): MainScreen{//means we are returning MainScreen
        loginBtn.perform(click())
        sleep(4000)
        return MainScreen()
    }

    //type in incorrect email
    //type in incorrect password
    //click on login
    //check toast message

    init {//first thing that is automatically executed whenever you create instance of the class (that we are on the right screen)
        uniqueView.check(matches(isDisplayed()))
    }
}