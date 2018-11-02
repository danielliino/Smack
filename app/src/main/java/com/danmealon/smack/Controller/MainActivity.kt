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
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.danmealon.smack.R
import com.danmealon.smack.Services.AuthService
import com.danmealon.smack.Services.UserDataService
import com.danmealon.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(   //drawer that comes out from the left on main activity
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //registering broadcast receiver with Intent type of IntentFilter (Intents type like radio stations can be different)
        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver,
            IntentFilter(BROADCAST_USER_DATA_CHANGE))
    }

    //creating receiver (when the user's data changes i.e. user login, our create a new user or logout, then we could call this receiver )

    private val userDataChangeReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {//when we receive a broadcast this function is called
            //whenever the broadcast has been sent out, whatever we wants then to happen, happens here
            if (AuthService.isLoggedIn){
                userNameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email
                //getting resource id
                val resourceId = resources.getIdentifier(UserDataService.avatarName,"drawable",
                    packageName)
                userImageNavHeader.setImageResource(resourceId)
                userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                loginBtnNavHeader.text = "Logout"
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

        if (AuthService.isLoggedIn){
        // log out (will clear out all user's data)
            UserDataService.logout()
            //setting everything back to default
            userNameNavHeader.text = "Login"
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

    }

    fun sendMsgBtnClicked(view:View){

    }
}
