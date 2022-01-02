package com.example.muslimchecklistmobile.content.home.prayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import com.example.muslimchecklistmobile.content.Data
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_isha.*
import java.text.SimpleDateFormat
import java.util.*


class IshaFragment : Fragment() {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid
    val dataClass = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        isha_date.text = currentDate
        database.collection("App Users").document(uid).collection("Dates").document(currentDate)
            .get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                            if (docs.get("Sona_Isha") != null) {
                                isha_sunah_btn.setBackgroundResource(R.drawable.check)
                            }
                            if (docs.get("Isha") != null) {
                                isha_prayer_tbtn.setBackgroundResource(R.drawable.check)
                            }} })


        isha_close.setOnClickListener {
            findNavController().navigateUp()
        }

        isha_sunah_btn.setOnClickListener {
                isha_sunah_btn.setBackgroundResource(R.drawable.check)
                dataClass.checkDocument("Sona_Isha")
        }
        isha_prayer_tbtn.setOnClickListener {
                isha_prayer_tbtn.setBackgroundResource(R.drawable.check)
                dataClass.checkDocument("Isha")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_isha, container, false)
    }
}