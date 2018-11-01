package com.danmealon.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.danmealon.smack.Utilities.URL_REGISTER
import org.json.JSONObject

object AuthService {

    //we call this Singleton from CreateUserActivity
    //To register a user we need to know email and password; for web request we also need a context and we also need lambda - functional literal

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) { //Unit means it returns nothing

        // we need a json body here, cause it is json object that we are passing along with web request so that when we hit the API it can check the json body if we have the expected parameters (email, password)
        val jsonBody = JSONObject()
        //we need to put key:value pairs into json Object
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        //we need our json body to be in String format so that we can convert it into byte array in later point
        val requestBody = jsonBody.toString()
        //now let's create a request (object of type StringRequest from volley library: in () we put method type, URL and the response listener)
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response -> //response->: means getting access to response
            println(response)
            complete(true) //if successfull
        }, Response.ErrorListener {error ->  //error->: means getting access to error
            Log.d("ERROR","Could not register user: $error")
            complete(false) ////if unsuccessfull
            // now we need to specify a body content type and add json body that we have declared, we can use override functions called getBodyContentType() and getBody()
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8" //this just a encoding standard
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        //once we have request created then we need to add it to request queue
        Volley.newRequestQueue(context).add(registerRequest)

    }
}