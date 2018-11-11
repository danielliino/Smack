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

    var channelName: String = ""
    var channelMessage: String = ""

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

    private val messageField: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.messageTextField))

    private val messageBtn: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.sendMessageBtn))

    fun addNewChannel() {
        channelBtn.perform(click())
        channelDialogName.perform(typeText(genName()))
        channelDialogDescription.perform(typeText(channelDescription))
        addBtn.perform(click())
    }

    private fun genName(): String {
      channelName ="TurkStreet # ${(Math.random() * 1000).toInt()}"
        return channelName
    }

    fun channelDisplayed():MainScreen {
        onView(ViewMatchers.withText("#$channelName"))
            .check(matches(isDisplayed()))
        return this
    }

    fun genMessage(): String {

        for (i in 0..10) {
            channelMessage = "What's up everybody # ${(Math.random() * 1000).toInt()}"
            messageField.perform(typeText(channelMessage))
            messageBtn.perform(click())
        }
        return channelMessage
    }

    init {//first thing that is automatically executed whenever you create instance of the class (that we are on the right screen)
        uniqueView.check(matches(isDisplayed()))
    }
}