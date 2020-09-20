package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

            handler = Handler()
            handler.postDelayed({
                val intent = Intent(this, Signin::class.java)
                startActivity(intent)
                finish()
            }, 3000) // delaying 3 seconds to open main activity

    }
}