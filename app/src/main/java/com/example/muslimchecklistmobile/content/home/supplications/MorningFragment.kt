package com.example.muslimchecklistmobile.content.home.supplications

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
import kotlinx.android.synthetic.main.fragment_morning.*
import java.text.SimpleDateFormat
import java.util.*


class MorningFragment : Fragment() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid
    val dataClass = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_morning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        database.collection("App Users").document(uid).collection("Dates").document(currentDate)
            .get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                            if (docs.get("Morning_Zekr") != null) {
                                morning_btn.setBackgroundResource(R.drawable.check)
                         } } })

        morning_close.setOnClickListener {
            findNavController().navigateUp()
        }
        morning_btn.setOnClickListener {
            morning_btn.setBackgroundResource(R.drawable.check)
            dataClass.checkDocument("Morning_Zekr")
        }
    }
}