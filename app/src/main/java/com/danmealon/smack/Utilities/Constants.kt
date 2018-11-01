package com.danmealon.smack.Utilities

const val BASE_URL = "https://chat-chat-chatt.herokuapp.com/v1/" //we added v1 as for API endpoint
//all other endpoint are going to be a combination of BASE_URL and other endpoint (like: account/register; /user/add etc)
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"//we hit this api endpoint when we want to login a user

