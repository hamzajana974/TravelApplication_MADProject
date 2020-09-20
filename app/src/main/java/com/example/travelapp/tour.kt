package com.example.travelapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class tour (
    var tid:String = "",
    var uid:String = "",
    var name:String="",
    var email:String="",
    var number:String ="",
    var title:String ="",
    var detail:String="",
    var mincost:String="",
    var maxcost:String="",
    var departure:String="",
    var destination:String="",
    var sdate:String="",
    var edate:String="",
    var seats:String="",
    var onlyAdults:String=""
)