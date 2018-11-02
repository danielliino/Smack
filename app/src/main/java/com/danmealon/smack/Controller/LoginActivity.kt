package com.danmealon.smack.Controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.danmealon.smack.R
import com.danmealon.smack.Services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginLoginBtnClicked(view: View){

        enableSpinner(true)
        val email = loginEmailTxt.text.toString()
        val password = loginPasswordText.text.toString()
        hideKeyboard()

        if (email.isNotEmpty() && password.isNotEmpty()){
            AuthService.loginUser(email,password){ loginSuccess ->
                if (loginSuccess) {
                    AuthService.findUserByEmail(this){ findSuccess ->
                        if (findSuccess) {
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
        } else{
            Toast.makeText(this, "Please fill in both email and password", Toast.LENGTH_LONG).show()
        }
    }

    fun loginCreateUserBtnClicked (view:View){
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }

    fun errorToast() {
        Toast.makeText(this,"Something went wrong, please try again!",
            Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }


    fun enableSpinner(enable: Boolean) {
        if (enable) {
            loginSpinner.visibility = View.VISIBLE //we want ot show it

        }else{
            loginSpinner.visibility = View.INVISIBLE
        }
        // we want to disable create user button, avatar image picker and color generator
        loginLoginBtn.isEnabled = !enable
        loginCreateUserBtn.isEnabled = !enable

    }
    //function that hides keyboard once we click login on login page

    fun hideKeyboard(){ //our keyboard is InputMethod that returns an object, and we cast that object as InputMethodManager
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //as means casting
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken,0) //windowToken that we are getting grubs whatever is in focus
        }

    }
}
