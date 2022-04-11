package com.nmssalman.qualitapps.voting

import android.content.Context
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nmssalman.qualitapps.Utilities.DialogBuilder

class FirebaseDatabase(private val listener: FirebaseListener, private val context: Context) {
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    init {
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                generateList(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                DialogBuilder.dialogBuilder(error.message, context).show()
            }

        })
    }
    fun addVoter(name: String, value: Int)
    {
        myRef.child(name).setValue(value).addOnSuccessListener {}.addOnFailureListener {
            DialogBuilder.dialogBuilder(it.message.toString(), context).show()
        }
    }
    fun getData(){
       myRef.get().addOnSuccessListener {
           generateList(it)
       }
    }
    fun generateList(list: DataSnapshot)
    {
        val array = ArrayList<Voters>()
        list.children.forEach {
            array.add(Voters(it.key!!, it.value.toString().toInt()))
        }
        listener.onReceive(array)
    }
    interface FirebaseListener{
        fun onReceive(list: ArrayList<Voters>)
    }
}