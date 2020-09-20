package com.example.travelapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_tour.*

class AddTour : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val context = this

    lateinit var title: String;
    lateinit var detail: String;
    lateinit var mincost: String;
    lateinit var maxcost: String;
    lateinit var departure: String;
    lateinit var destination: String;
    lateinit var sdate: String;
    lateinit var edate: String;
    lateinit var seats: String;
    lateinit var onlyAdults: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tour)

        supportActionBar?.hide()

        onlyAdults = ""
        auth = Firebase.auth
        auth = Firebase.auth
        database = Firebase.database

        ed_sdate.addTextChangedListener(object : TextWatcher {
            var prevL = ed_sdate.getText().toString().trim();

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                var length = s?.length;
                if ((prevL.length < 11) && (length == 2 || length == 5)) {
                    var data = ed_sdate.getText().toString();
                    ed_sdate.setText(data + "-");
                    ed_sdate.setSelection(length + 1)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if ((prevL.length < 11) && (prevL.length == 2 || prevL.length == 5)) {
                    var data = ed_sdate.getText().toString();
                    ed_sdate.setText(data + "-");
                }
            }
        })
        ed_edate.addTextChangedListener(object : TextWatcher {
            var prevL = ed_edate.getText().toString().trim();

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                var length = s?.length;
                if ((prevL.length < 11) && (length == 2 || length == 5)) {
                    var data = ed_edate.getText().toString();
                    ed_edate.setText(data + "-");
                    ed_edate.setSelection(length + 1)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if ((prevL.length < 11) && (prevL.length == 2 || prevL.length == 5)) {
                    var data = ed_edate.getText().toString();
                    ed_edate.setText(data + "-");
                }
            }
        })
        btn_add.setOnClickListener {
            title = ed_title.text.toString().trim()
            detail = ed_detail.text.toString().trim()
            mincost = ed_mincost.text.toString().trim()
            maxcost = ed_maxcost.text.toString().trim()
            departure = ed_depart.text.toString().trim()
            destination = ed_dest.text.toString().trim()
            sdate = ed_sdate.text.toString().trim()
            edate = ed_edate.text.toString().trim()
            seats = ed_seats.text.toString().trim()

            if (onlyAdults.isEmpty()) {
                Toast.makeText(this, "Select if only adults are allowed", Toast.LENGTH_LONG).show()
            } else {
                if (title.isEmpty() && detail.isEmpty() && mincost.isEmpty() && maxcost.isEmpty() && departure.isEmpty()
                    && destination.isEmpty() && sdate.isEmpty() && edate.isEmpty() && seats.isEmpty()
                ) {
                    Toast.makeText(this, "All fields are requied", Toast.LENGTH_LONG)
                        .show()
                } else {
//                    Utils.showToast(context, "in tour.")

                    if (mincost.toInt() <= maxcost.toInt()) {
//                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val currentUser = auth.currentUser
                        if (currentUser != null) {

                            val myRef = database.getReference("users")
                            myRef.child(currentUser.uid).addValueEventListener(object:
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val user = snapshot.value as? HashMap<String,String>
                                    val fn= user?.get("fn")
                                    val ln= user?.get("ln")
                                    val email = user?.get("email")
                                    val number = user?.get("number")
                                    val name = "$fn $ln"
                                    val uid = user?.get("uid")

                                    Toast.makeText(this@AddTour, "$uid $name $email", Toast.LENGTH_LONG)
                                        .show()
                                    val ref = FirebaseDatabase.getInstance().getReference("tours")
                                    val tourid = ref.push().key
                                    val tid = tourid

                                    val tour = tid?.let { it1 ->
                                        tour(
                                            it1,
                                            uid!!,
                                            name,
                                            email!!,
                                            number!!,
                                            title,
                                            detail,
                                            mincost,
                                            maxcost,
                                            departure,
                                            destination,
                                            sdate,
                                            edate,
                                            seats,
                                            onlyAdults
                                        )
                                    }
                                    if(tour != null){
                                        ref.child(tid).setValue(tour).addOnCompleteListener{
                                        }
                                        Toast.makeText(
                                            this@AddTour,
                                            "Data uploaded",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }else{
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    Toast.makeText(
                                        this@AddTour,
                                        "Error fetching data",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                            })


                        }


                    } else {
                        Toast.makeText(
                            this,
                            "Min cost must be less than max cost",
                            Toast.LENGTH_LONG
                        )
                            .show()

                    }
                }
            }
        }


    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.adYes ->
                    if (checked) {
                        onlyAdults = "Yes"
                    }
                R.id.adNo ->
                    if (checked) {
                        onlyAdults = "No"
                    }
            }
        }
    }
}