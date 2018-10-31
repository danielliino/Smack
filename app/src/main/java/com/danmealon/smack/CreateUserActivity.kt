package com.danmealon.smack

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"//name of our default image in drawables
    var avatarColor = "[0.5, 0.5, 0.5, 1]"//default avatar color

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
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

    }
}
