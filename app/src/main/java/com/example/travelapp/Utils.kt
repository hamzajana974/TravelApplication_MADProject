package com.example.travelapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

//import com.google.firebase.analytics.ktx.analytics
//import com.google.firebase.ktx.Firebase

object Utils {
    fun validateInputs(vararg inputs:String):Boolean{
        for(input in inputs){
            if(input.isNullOrEmpty())
                return false
        }
        return true
    }

    fun validatePasswords(pass:String,conPass:String):Boolean{
        if(pass.contentEquals(conPass))
            return true
        return false
    }

    fun getText(editText: EditText) = editText.text.toString().trim()


    fun <T> goToActivity(context: Context, c:Class<T>){
        Intent(context,c).apply {
            context.startActivity(this)
        }
    }

    fun showToast(context: Context, mess:String){
        Toast.makeText(context,mess, Toast.LENGTH_SHORT).show()
    }

    fun logEvent(eventName:String,bundle: Bundle){
        Firebase.analytics.logEvent(eventName,bundle)
    }
}