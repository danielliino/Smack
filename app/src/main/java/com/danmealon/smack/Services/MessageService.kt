package com.danmealon.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.danmealon.smack.Controller.App
import com.danmealon.smack.Model.Channel
import com.danmealon.smack.Model.Message
import com.danmealon.smack.Utilities.IdlingResourceHolder
import com.danmealon.smack.Utilities.URL_GET_CHANNELS
import com.danmealon.smack.Utilities.URL_GET_MESSAGES
import org.json.JSONArray
import org.json.JSONException

object MessageService {

    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    //function that that traverse an array of channels.

    fun getChannels(complete: (Boolean)->Unit){
        IdlingResourceHolder.idlingResource.increment()
        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener { response ->

            //looping and extracting info that we need

            try {
                for (x in 0 until response.length()){
                    //creating json objects before traversing
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val chanDesc = channel.getString("description")
                    val channelId = channel.getString("_id")
                    //creating new channel
                    val newChannel = Channel(name, chanDesc, channelId)
                    //adding channel to arraylist of channels
                    this.channels.add(newChannel)
                }
                complete(true)

            }catch (e: JSONException){
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            } finally {
                IdlingResourceHolder.idlingResource.decrement()
            }


        }, Response.ErrorListener { error ->
           try {
               Log.d("ERROR", "Could not retrieve channels")
               complete(false)
           }finally {
               IdlingResourceHolder.idlingResource.decrement()
           }
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"//tells us what kind of encoding to do
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }
        App.prefs.requestQueue.add(channelsRequest)
    }

    fun getMessages(channelId: String, complete: (Boolean) -> Unit) {
        val url = "$URL_GET_MESSAGES$channelId"

        val messagesRequest = object : JsonArrayRequest(Method.GET, url, null, Response.Listener { response ->

            clearMessages()
                try {
                    for (x in 0 until response.length()){
                        val message = response.getJSONObject(x)
                        val messageBody = message.getString("messageBody")
                        val channelId = message.getString("channelId")
                        val id = message.getString("_id")
                        val userName = message.getString("userName")
                        val userAvatar = message.getString("userAvatar")
                        val userAvatarColor = message.getString("userAvatarColor")
                        val timeStamp = message.getString("timeStamp")

                        val newMessage = Message(messageBody, userName, channelId, userAvatar, userAvatarColor, id, timeStamp)
                        this.messages.add(newMessage)
                    }

                    complete(true)

                }catch (e: JSONException){
                    Log.d("JSON", "EXC:" + e.localizedMessage)
                    complete(false)
                }



        }, Response.ErrorListener { 
            Log.d("ERROR", "Could not retrieve channels")
            complete(false)
        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"//tells us what kind of encoding to do
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }

        }
        App.prefs.requestQueue.add(messagesRequest)
    }

    fun clearMessages(){
        messages.clear()
    }

    fun clearChannels(){
        channels.clear()
    }
}