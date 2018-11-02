package com.danmealon.smack.Model

class Channel (val name: String, val description: String, val id: String) {
    //creating listView
    override fun toString(): String {
        return "#$name" //it is a modern standard that channels have # on front
    }
}