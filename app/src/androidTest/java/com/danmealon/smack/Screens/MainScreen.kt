package com.danmealon.smack.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.danmealon.smack.R

class MainScreen : BaseScreen() {

    private val channelName = "TurkStreet Channel"
    private val channelDescription = "First added channel using Espresso (Kotlin based)"

    //click on channelBtn
    private val channelBtn: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.addChannelBtn))

    //putting unique element to make sure we are on LoginScreen by using constructor
    override val uniqueView: ViewInteraction
        get() = channelBtn

    //enter channel name
    private val channelDialogName: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.addChannelNameTxt))

    //enter channel description
    private val channelDialogDescription: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.addChannelDescTxt))

    //click on addBtn and check if new channel is displayed
    private val addBtn: ViewInteraction
        get() = onView(ViewMatchers.withText("ADD"))

    fun clickOnChannelBtn():MainScreen{
        channelBtn.perform(click())
        return this
    }

    fun enterChannelName():MainScreen{
        channelDialogName.perform(typeText(channelName))
        return this
    }

    fun enterChannelDescription():MainScreen{
        channelDialogDescription.perform(typeText(channelDescription))
        return this
    }

    fun clickOnAddBtn():MainScreen{
        addBtn.perform(click())
        return this
    }

    fun channelDisplayed():MainScreen {
        onView(ViewMatchers.withText("#TurkStreet Channel"))
            .check(matches(isDisplayed()))
        return this
    }

    init {//first thing that is automatically executed whenever you create instance of the class (that we are on the right screen)
        uniqueView.check(matches(isDisplayed()))
    }
}