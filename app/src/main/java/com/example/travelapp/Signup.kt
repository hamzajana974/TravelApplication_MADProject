    package com.example.travelapp

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.ktx.auth
    import com.google.firebase.database.ktx.database
    import com.google.firebase.ktx.Firebase
    import kotlinx.android.synthetic.main.activity_signin.*
    import kotlinx.android.synthetic.main.activity_signin.btn_signup
    import kotlinx.android.synthetic.main.activity_signin.ed_password
    import kotlinx.android.synthetic.main.activity_signup.*

    class Signup : AppCompatActivity() {
        private lateinit var auth: FirebaseAuth
        private val context = this
        val database = Firebase.database
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)


            supportActionBar?.hide()

            auth = Firebase.auth
            btn_signup.setOnClickListener(){
                signup()
            }

        }
        private fun signup() {
            val fn = Utils.getText(ed_Fname)
            val ln = Utils.getText(ed_Lname)
            val email = Utils.getText(ed_Email)
            val number = Utils.getText(ed_Number)
            val password = Utils.getText(ed_password)
            val confPassword = Utils.getText(ed_conpass)

            if(Utils.validateInputs(fn,ln,email,number,password,confPassword)){
                if(password.length>=6){
                    if(Utils.validatePasswords(password,confPassword)){
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser

                                val us = User(user?.uid,fn,ln,email,number)
                                val database = Firebase.database
                                val myRef = database.getReference("users")
                                myRef.child(user!!.uid).setValue(us)

                                Utils.showToast(context,"Sign up successfull.")
                                Utils.goToActivity(this,DashboardActivity::class.java)
                                finish()

                            } else {
                                Utils.showToast(context,"Sign up failed.")
                            }
                        }

                    }
                    else{
                        Utils.showToast(this,"Passwords must be same")
                    }
                }
                else{
                    Utils.showToast(this,"Passwords must be atleast 6 characters")
                }
            }
            else{
                Utils.showToast(this,"All fields are required")
            }

        }
    }