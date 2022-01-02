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
import kotlinx.android.synthetic.main.fragment_fajr.*
import java.text.SimpleDateFormat
import java.util.*

class FajrFragment : Fragment() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid
    val dataClass = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        fajr_date.text = currentDate

        database.collection("App Users").document(uid).collection("Dates").document(currentDate)
            .get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                    if (docs.get("Fajr") != null)
                    { fajr_prayer_tbtn.setBackgroundResource(R.drawable.check)
                    }
                    if (docs.get("Sona_Fajr") != null) {
                        fajr_sunah_tbtn.setBackgroundResource(R.drawable.check)
                    }
                } } )

        fajr_close.setOnClickListener {
            findNavController().navigateUp()
        }
        fajr_prayer_tbtn.setOnClickListener {
            fajr_prayer_tbtn.setBackgroundResource(R.drawable.check)
            dataClass.checkDocument("Fajr")
        }
        fajr_sunah_tbtn.setOnClickListener {
            fajr_sunah_tbtn.setBackgroundResource(R.drawable.check)
            dataClass.checkDocument("Sona_Fajr")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fajr, container, false)
    }

}