package com.danmealon.smack.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.danmealon.smack.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginScreen {

    //OnView returns ViewInteraction class.
    //ViewInteraction. Provides the primary interface to perform actions or asserts on views.
    //Each interaction is associated with a view identified by a view matcher.
    //All view actions and asserts are performed on the UI thread
    //(ensuring sequential execution).

    val email = "w@w.com"
    val password = "123456"

    //type in email
    val editTextEmail: ViewInteraction
    get() = onView(withId(R.id.loginEmailTxt))

    fun enterEmail(){
        editTextEmail.perform(typeText(email))
    }

    //type in password
    val editTextPassword: ViewInteraction
    get() = onView(withId(R.id.loginPasswordText))

    fun enterPassword(){
        editTextPassword.perform(typeText(password))
    }

    //click on login
    val loginBtn: ViewInteraction //the type of this variable is ViewInteraction
    get() = onView(withId(R.id.loginLoginBtn)) //we find the id of the btn

    fun clickOnLoginBtn(){
        loginBtn.perform(click())

    }
}