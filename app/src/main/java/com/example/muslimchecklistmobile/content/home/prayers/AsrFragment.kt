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
import kotlinx.android.synthetic.main.fragment_asr.*
import java.text.SimpleDateFormat
import java.util.*


class AsrFragment : Fragment() {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid
    private val dataClass = Data()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        asr_date.text = currentDate
        database.collection("App Users").document(uid).collection("Dates").document(currentDate)
            .get().addOnCompleteListener { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                    if (docs.get("Asr") != null) {
                        asr_prayer_tbtn.setBackgroundResource(R.drawable.check)
                    }
                }
            }

        asr_close.setOnClickListener {
            findNavController().navigateUp()
        }

        asr_prayer_tbtn.setOnClickListener {
                asr_prayer_tbtn.setBackgroundResource(R.drawable.check)
                dataClass.checkDocument("Asr")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asr, container, false)
    }
}