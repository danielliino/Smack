package com.danmealon.smack.Services

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.danmealon.smack.Controller.App
import com.danmealon.smack.Utilities.*
import org.json.JSONException
import org.json.JSONObject

object AuthService {

    //we call this Singleton from CreateUserActivity
    //To register a user we need to know email and password; for web request we also need a context and we also need lambda - functional literal

    fun registerUser(email: String, password: String, complete: (Boolean) -> Unit) { //Unit means it returns nothing

        // we need a json body here, cause it is json object that we are passing along with web request so that when we hit the API it can check the json body if we have the expected parameters (email, password)
        val jsonBody = JSONObject()
        //we need to put key:value pairs into json Object
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        //we need our json body to be in String format so that we can convert it into byte array in later point
        val requestBody = jsonBody.toString()
        //now let's create a request (object of type StringRequest from volley library: in () we put method type, URL and the response listener)
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response -> //response->: means getting access to response
            complete(true) //if successfull
        }, Response.ErrorListener {error ->  //error->: means getting access to error
            Log.d("ERROR","Could not register user: $error")
            complete(false) //if unsuccessfull
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
        App.prefs.requestQueue.add(registerRequest)

    }

    fun loginUser(email: String, password: String, complete: (Boolean) -> Unit){

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        //we expecting to get JsonObject not a String, we put null because we are not passing the object here
        val loginRequest = object: JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener {response->
            //this is where we parse json object
            //getString() method throws an exception that means something could go wrong, you have to handle the exceptions by putting them in try-catch block (hover a mouse above the function and click control to read the documentation)
            //try-catch means: try this thing and if not catch the exception that occurs, check logs to see if exception works
            try{

                //we are entering json object and then saying get a string (value that is returned from API), and then we access it by the key (token)
                App.prefs.userEmail = response.getString("user")
                App.prefs.authToken = response.getString("token")
                App.prefs.isLoggedIn = true
                complete(true)

            }catch (e: JSONException){
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            }

        },Response.ErrorListener {error->
            //this is where we deal with error
            Log.d("ERROR","Could not login user: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return return "application/json; charset=utf-8"
            }
            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        //adding to request queue
        App.prefs.requestQueue.add(loginRequest)
        }

    fun createUser(name: String, email: String, avatarName: String, avatarColor: String, complete: (Boolean) -> Unit) {

        //creating body
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName",avatarName)
        jsonBody.put("avatarColor",avatarColor)
        val requestBody = jsonBody.toString()

        //creating request

        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->

            try {

                //we are going to access value of our json object and then save them into user data service variables
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")
                complete(true)

            }catch (e:JSONException){
                Log.d("JSON","EXC" + e.localizedMessage)
                complete(false)
            }
        }, Response.ErrorListener { error->
            //this is where we deal with error
            Log.d("ERROR","Could not register user: $error")
            complete(false)

        })
            {
                override fun getBodyContentType(): String {
                return return "application/json; charset=utf-8"
            }
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()
                }
                //we need to a header cause we are dealing with locked API endpoint, cause othervise we will hit to 401 error
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                    return headers
                }
            }
        App.prefs.requestQueue.add(createRequest)
    }

    fun findUserByEmail (context: Context, complete: (Boolean) -> Unit) {
        //creating web request; concatinating endpoint
        val findUserRequest = object: JsonObjectRequest(Method.GET, "$URL_GET_USER${App.prefs.userEmail}",null,Response.Listener { response->

            //extracting info by the key and save it in userDataService
            try{
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")

                //sending broadcast
                val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                LocalBroadcastManager.getInstance(context).sendBroadcast(userDataChange)
                complete(true)

            }catch (e: JSONException){
                Log.d("JSON", "EXC: " + e.localizedMessage)
            }

        }, Response.ErrorListener {error ->
            Log.d("ERROR", "Could not find user")
            complete(false)

        }) {
            override fun getBodyContentType(): String {
                return return "application/json; charset=utf-8"
            }

            //we need to a header cause we are dealing with locked API endpoint, cause othervise we will hit to 401 error
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }
        App.prefs.requestQueue.add(findUserRequest)
    }

    }
