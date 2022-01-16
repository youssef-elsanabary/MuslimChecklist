package com.example.muslimchecklistmobile.content.home.prayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import com.example.muslimchecklistmobile.content.Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_zhr.*
import java.text.SimpleDateFormat
import java.util.*


class ZhrFragment : Fragment() {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid
    private val dataClass = Data()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        zhr_date.text = currentDate

        database.collection("App Users").document(uid).collection("Dates").document(currentDate)
            .get().addOnCompleteListener { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                    if (docs.get("Before_Sona_Duhr ") != null) {
                        zhr_sunah_before_btn.setBackgroundResource(R.drawable.check)
                    }
                    if (docs.get("After_Sona_Duhr") != null) {
                        zhr_sunah_after_btn.setBackgroundResource(R.drawable.check)
                    }
                    if (docs.get("Duhr") != null) {
                        zhr_prayer_btn.setBackgroundResource(R.drawable.check)
                    }
                }
            }




        zhr_close.setOnClickListener {
            findNavController().navigateUp()
        }

        zhr_sunah_before_btn.setOnClickListener {
                zhr_sunah_before_btn.setBackgroundResource(R.drawable.check)
                dataClass.checkDocument("Before_Sona_Duhr ")
        }
        zhr_sunah_after_btn.setOnClickListener {
                zhr_sunah_after_btn.setBackgroundResource(R.drawable.check)
                dataClass.checkDocument("After_Sona_Duhr")
        }
        zhr_prayer_btn.setOnClickListener {
                zhr_prayer_btn.setBackgroundResource(R.drawable.check)
                dataClass.checkDocument("Duhr")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zhr, container, false)
    }
}