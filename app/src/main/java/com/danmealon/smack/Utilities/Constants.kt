package com.danmealon.smack.Utilities

const val BASE_URL = "https://chat-chat-chatt.herokuapp.com/v1/" //we added v1 as for API endpoint
const val SOCKET_URL = "https://chat-chat-chatt.herokuapp.com/"
//all other endpoint are going to be a combination of BASE_URL and other endpoint (like: account/register; /user/add etc)
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"//we hit this api endpoint when we want to login a user
const val URL_CREATE_USER = "${BASE_URL}user/add"

const val URL_GET_USER = "${BASE_URL}user/byEmail/"
const val URL_GET_CHANNELS = "${BASE_URL}channel/"
const val URL_GET_MESSAGES = "${BASE_URL}message/byChannel/"

//broadcast constants

const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"

