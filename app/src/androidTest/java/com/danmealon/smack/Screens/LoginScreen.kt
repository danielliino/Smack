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

class LoginScreen: BaseScreen() {

    //OnView returns ViewInteraction class.
    //ViewInteraction. Provides the primary interface to perform actions or asserts on views.
    //Each interaction is associated with a view identified by a view matcher.
    //All view actions and asserts are performed on the UI thread
    //(ensuring sequential execution).

    private val email = "w@w.com"
    private val password = "123456"

    //type in email
    private val editTextEmail: ViewInteraction
    get() = onView(withId(R.id.loginEmailTxt))

    fun enterEmail(){
        editTextEmail.perform(typeText(email))
    }

    //type in password
    private val editTextPassword: ViewInteraction
    get() = onView(withId(R.id.loginPasswordText))

    fun enterPassword(){
        editTextPassword.perform(typeText(password))
    }

    //click on login
    private val loginBtn: ViewInteraction //the type of this variable is ViewInteraction
    get() = onView(withId(R.id.loginLoginBtn)) //we find the id of the btn

    fun clickOnLoginBtn(): MainScreen{//means we are returning MainScreen
        loginBtn.perform(click())
        return MainScreen()
    }

    //putting unique element to make sure we are on LoginScreen by using constructor
    override val uniqueView: ViewInteraction
        get() = loginBtn

    init {//first thing that is automatically executed whenever you crete instance of the class (that we are on the right screen)
        uniqueView.check(matches(isDisplayed()))
    }
}