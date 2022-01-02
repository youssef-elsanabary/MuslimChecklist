package com.example.muslimchecklistmobile.content

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class Data {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore= FirebaseFirestore.getInstance()
    var currentDate: String =
        SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid

    fun updateData(btn_name: String) {
        val map: MutableMap<String, Any> = HashMap()
        map[btn_name] = "1"

        database.collection("App Users").document(uid)
            .collection("Dates").document(currentDate)
            .update(map)
    }
    fun checkDocument (btn: String){
        val map: MutableMap<String, Any> = HashMap()
        map[btn] = "1"
        database.collection("App Users").document(uid)
            .collection("Dates").document(currentDate)
            .get().addOnCompleteListener (OnCompleteListener<DocumentSnapshot> { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                    if (docs.exists()){
                        updateData(btn)
                    }else
                        database.collection("App Users").document(uid)
                            .collection("Dates").document(currentDate).set(map)
                    } })
    }

}