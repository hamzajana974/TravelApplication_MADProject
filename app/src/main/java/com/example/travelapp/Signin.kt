package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*


class Signin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        supportActionBar?.hide()

        auth = Firebase.auth
        btn_login.setOnClickListener(){
            makeLogin()
        }

        btn_signup.setOnClickListener(){

            Intent(this,Signup::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
    private fun makeLogin(){
        val email = Utils.getText(ed_email)
        val password = Utils.getText(ed_password)

        if(Utils.validateInputs(email,password)){
            //Authenticate Firebase User
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Utils.showToast(context,"Authenticated successfully.")

                        Utils.goToActivity(this,DashboardActivity::class.java)
                        finish()

                    } else {
                        Utils.showToast(context,"Invalid Credentials")
                    }
                }
        }
        else{
            Utils.showToast(this,"Please enter your credentials")
        }
    }
}