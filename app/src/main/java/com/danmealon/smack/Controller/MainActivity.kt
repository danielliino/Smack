package com.danmealon.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import com.danmealon.smack.Model.Channel
import com.danmealon.smack.R
import com.danmealon.smack.Services.AuthService
import com.danmealon.smack.Services.MessageService
import com.danmealon.smack.Services.UserDataService
import com.danmealon.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import com.danmealon.smack.Utilities.SOCKET_URL
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    //socket creation
    val socket = IO.socket(SOCKET_URL)
    //creating an adapter for list view if Channels type
    lateinit var channelAdapter: ArrayAdapter<Channel>
    private fun setupAdapters(){
        channelAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MessageService.channels)
        //adding adapter to list view
        channel_list.adapter = channelAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        socket.connect()
        //receiving data ("on")
        socket.on("channelCreated", onNewChannel)

        val toggle = ActionBarDrawerToggle(   //drawer that comes out from the left on main activity
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setupAdapters()

        //check to see if we logged in, if we are that we will update the UI and start downloading channels
        //so even after we stopped emulator we are still logged in
        if (App.prefs.isLoggedIn){
            AuthService.findUserByEmail(this){}
        }


    }

    //connecting socket to API
    //remember Activity life cycle

    override fun onResume() {
        //registering broadcast receiver with Intent type of IntentFilter (Intents type like radio stations can be different); note that you need to unregister it when you leave the activity
        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver,
            IntentFilter(BROADCAST_USER_DATA_CHANGE))

        super.onResume()
    }

    override fun onDestroy() {
        //we destroy when we disconnecting from socket connection
        socket.disconnect()
        //unregistering broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(userDataChangeReceiver)
        super.onDestroy()
    }

    //creating receiver (when the user's data changes i.e. user login, our create a new user or logout, then we could call this receiver )

    private val userDataChangeReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {//when we receive a broadcast this function is called
            //whenever the broadcast has been sent out, whatever we wants then to happen, happens here
            if (App.prefs.isLoggedIn){
                userNameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email
                //getting resource id
                val resourceId = resources.getIdentifier(UserDataService.avatarName,"drawable",
                    packageName)
                userImageNavHeader.setImageResource(resourceId)
                userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                loginBtnNavHeader.text = "Logout"
                //calling getChannels() function that tells the adapter that the data is changed so we need to reload listView
                MessageService.getChannels(context){ complete ->
                    if(complete){
                        channelAdapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginBtnNavClicked(view:View){ //view:View to import View class

        if (App.prefs.isLoggedIn){
        // log out (will clear out all user's data)
            UserDataService.logout()
            //setting everything back to default
            userNameNavHeader.text = ""
            userEmailNavHeader.text = ""
            userImageNavHeader.setImageResource(R.drawable.profiledefault)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            loginBtnNavHeader.text = "Login"

        }else{
            //explicit intent as we know where we are sending intent to
            val loginIntent = Intent(this, LoginActivity::class.java)//this - context; LoginActivity - where we are navigating
            startActivity(loginIntent)
        }
    }

    fun addChannelClicked(view:View){

        //creating alert dialog
        if (App.prefs.isLoggedIn){
            val builder = AlertDialog.Builder(this)
            //creating alert dialog view by using inflater (tool that creates view from xml)
            val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog, null)
            //building our dialog
            builder.setView(dialogView)
                .setPositiveButton("Add"){ dialogInterface, i ->
                    //perform some logic when clicked
                    //referencing to ui elements
                    val nameTextField = dialogView.findViewById<EditText>(R.id.addChannelNameTxt)
                    val descTextField = dialogView.findViewById<EditText>(R.id.addChannelDescTxt)
                    //get text out of these fields
                    val channelName = nameTextField.text.toString()
                    val channelDesc = descTextField.text.toString()

                    //Create channel with the channel name and description
                    socket.emit("newChannel", channelName, channelDesc)

                }
                .setNegativeButton("Cancel"){dialogInterface, i ->
                    //cancel and close the dialog

                }
                .show()

        }
    }

    //emit listener creation, we use the array of type any

    private val onNewChannel = Emitter.Listener { args ->  //background thread
        //we say that this listener is on background/ worker thread and we need to get back to main thread to Update our UI
        runOnUiThread {
            //we are back on UI thread now, we can update our listView
            val channelName = args[0] as String
            val channelDescription =  args[1] as String
            val channelId = args[2] as String

            //new channel object creation
            val newChannel = Channel(channelName, channelDescription, channelId)
            //saving object in message service channel array
            MessageService.channels.add(newChannel)

            channelAdapter.notifyDataSetChanged()
        }
    }


    fun sendMsgBtnClicked(view:View){
        hideKeyboard()

    }

    //function that hides keyboard once we click login on login page

    fun hideKeyboard(){ //our keyboard is InputMethod that returns an object, and we cast that object as InputMethodManager
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //as means casting
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken,0) //windowToken that we are getting grubs whatever is in focus
        }

    }
}
