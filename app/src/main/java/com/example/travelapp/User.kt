package com.example.travelapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uid: String? = "",
    var fn: String? = "",
    var ln: String? = "",
    var email: String? = "",
    var number: String? = ""
)