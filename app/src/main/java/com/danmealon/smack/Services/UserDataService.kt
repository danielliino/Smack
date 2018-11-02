package com.danmealon.smack.Services

import java.util.*

object UserDataService {

    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    //function to store background color, we returning int instead of color because it is the way android works, when you create a color it is actually an int
    fun returnAvatarColor(components: String) : Int {
        //we have open bracket, decimal number(RED), comma, space, decimal number(GREEN),comma, space, decimal number(blue),comma, space, alpha value(1)
        //we want to turn decimal numbers to int between 0-255 by getting rid of commas, brackets by using replace() function
        val strippedColor = components
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")

        //now we need to pick up numbers in the string (strippedColor), by using Scanner which can traverse string and pull out some elements bsed on certain characters
        //so we need to pull out double values, first we need to store them
        var r = 0;
        var g = 0;
        var b = 0;

        val scanner = Scanner(strippedColor)

    }
}