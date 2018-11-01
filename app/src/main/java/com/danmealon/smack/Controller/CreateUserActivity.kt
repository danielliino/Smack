package com.danmealon.smack.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast

import com.danmealon.smack.R
import com.danmealon.smack.Services.AuthService
import com.danmealon.smack.Services.UserDataService
import com.danmealon.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"//name of our default image in drawables
    var avatarColor = "[0.5, 0.5, 0.5, 1]"//default avatar color

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view:View){

        val random = Random()//creating random number generator
        val color = random.nextInt(2)//randomly generates next integer between 0 and 1
        val avatar = random.nextInt(28)//randomly generates next integer between 0 and 27

        //if number is 0 - light, if 1 - dark
        if (color == 0){
            userAvatar = "light$avatar" //concatinates string + int
        } else{
            userAvatar = "dark$avatar"
        }
        //getting resource id based on the name of the resource
        val resourceId = resources.getIdentifier(userAvatar,"drawable",packageName)//all parameters are strings
        //setting image that is equals to that resourceId
        createAvatarImageView.setImageResource(resourceId)//createAvatarImageView - id of the imageView
    }

    fun generateColorClicked(view:View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        //this is for IOS users, value that is going to be saved to the API database
        val savedR = r.toDouble()/255//converting to double, equavalent to 255 in 0-1 scale
        val savedG = r.toDouble()/255
        val savedB = r.toDouble()/255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun createUserClicked(view:View){

        enableSpinner(true)
        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()//password is an id of username field

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){

            AuthService.registerUser(this, email,password){registerSuccess->
                if (registerSuccess){
                    AuthService.loginUser(this, email, password) {loginSuccess->
                        if(loginSuccess){
                            AuthService.createUser(this,userName,email,userAvatar, avatarColor) {createSuccess->
                                if (createSuccess){

                                    //implementing broadcast by using the intent of type action and string
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)

                                    enableSpinner(false)
                                    finish()
                                } else{
                                    errorToast()
                                }
                            }
                        }else{
                            errorToast()
                        }
                    }

                }else{
                    errorToast()
                }

            }
        } else { //if one of them is empty
            Toast.makeText(this,"Make sure user's name, email and password are filled in",
                Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }

    }

    fun errorToast() {
        Toast.makeText(this,"Something went wrong, please try again!",
            Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }


    fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE //we want ot show it

        }else{
            createSpinner.visibility = View.INVISIBLE
        }
        // we want to disable create user button, avatar image picker and color generator
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable

    }

}
