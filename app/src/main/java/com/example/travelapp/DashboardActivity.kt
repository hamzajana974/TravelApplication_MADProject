package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportActionBar?.hide()

        auth = Firebase.auth
        database = Firebase.database

        btn_signout.setOnClickListener {
            singOut()
        }

btn_addtour.setOnClickListener {

Utils.goToActivity(this,AddTour::class.java)


}
        btn_exploretour.setOnClickListener {

            Utils.goToActivity(this,MyTours::class.java)

    }
    }
    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Go to Dashboard
            val myRef = database.getReference("users")
            myRef.child(currentUser.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.value as HashMap<String, String>
                    val fn = user.get("fn")
                    val ln = user.get("ln")
                    val gender = user.get("gender")
                    txt_user.text = "$fn $ln"

                    val bundle = Bundle()
                    bundle.putString("username", "$fn $ln")
                    Utils.logEvent(Constants.OPEN_DASHBOARD, bundle)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


        }
    }
        private fun singOut() {
            auth.signOut()
            Utils.goToActivity(this, MainActivity::class.java)
            finish()
        }

    private fun maintain(){

        val currentUser = FirebaseAuth.getInstance().currentUser
//        val currentUser = auth.currentUser
        if(currentUser!=null){
            val myRef = database.getReference("users")
            myRef.child(currentUser.uid).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.value as? HashMap<String,String>
                    val fn= user?.get("fn")
                    val ln= user?.get("ln")
                    txt_user.text = "$fn $ln"
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    }