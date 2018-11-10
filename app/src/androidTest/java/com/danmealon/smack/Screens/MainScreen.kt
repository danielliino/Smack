package com.danmealon.smack.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.danmealon.smack.R

class MainScreen {

    private val channelName = "TurkStreet Channel"
    private val channelDescription = "First added channel using Espresso (Kotlin based)"

    //click on channelBtn
    private val channelBtn: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.addChannelBtn))

    fun clickOnChannelBtn(){
        channelBtn.perform(click())
    }

    //enter channel name
    private val channelDialogName: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.addChannelNameTxt))

    fun enterChannelName(){
        channelDialogName.perform(typeText(channelName))
    }

    //enter channel description
    private val channelDialogDescription: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.addChannelDescTxt))

    fun enterChannelDescription(){
        channelDialogDescription.perform(typeText(channelDescription))
    }

    //click on addBtn and check if new channel is displayed
    private val addBtn: ViewInteraction
        get() = onView(ViewMatchers.withText("ADD"))

    fun clickOnAddBtn(){
        addBtn.perform(click())
        onView(ViewMatchers.withText("#TurkStreet Channel"))
            .check(matches(isDisplayed()))
    }
}