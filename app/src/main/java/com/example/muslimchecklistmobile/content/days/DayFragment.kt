package com.example.muslimchecklistmobile.content.days

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.muslimchecklistmobile.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import kotlinx.android.synthetic.main.fragment_day.*
import java.text.SimpleDateFormat
import java.util.*


class DayFragment : Fragment() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid

    fun checkIfPrayed(prayValue: Any?, btnName: ImageView) {
        if (prayValue != null) {
            btnName.setBackgroundResource(R.drawable.check)
        } else {
            btnName.setBackgroundResource(R.drawable.unchecked)
        }
    }

    fun checkIfPrayAll() {
        getFBData { docs ->
            if (docs!!.exists()) {
                val prog = docs!!.data
                val progressCount = prog!!.size
                day_progress.progress = progressCount
                val progressValue = day_progress.progress
                progress_text.text = progressValue.toString()
            }
            checkIfPrayed(docs?.get("Fajr"), fajr_btn)
            checkIfPrayed(docs?.get("Sona_Fajr"), fajr_sunnah_btn)
            checkIfPrayed(docs?.get("Duha"), duha_btn)
            checkIfPrayed(docs?.get("Before_Sona_Duhr"), before_duhr_btn)
            checkIfPrayed(docs?.get("After_Sona_Duhr"), after_duhr_btn)
            checkIfPrayed(docs?.get("Duhr"), duhr_btn)
            checkIfPrayed(docs?.get("Asr"), asr_btn)
            checkIfPrayed(docs?.get("Sona_Maghreb"), after_maghreb_btn)
            checkIfPrayed(docs?.get("Maghreb"), maghreb_btn)
            checkIfPrayed(docs?.get("Sona_Isha"), after_isha_btn)
            checkIfPrayed(docs?.get("Isha"), isha_btn)
            checkIfPrayed(docs?.get("Morning_Zekr"), morning_btn)
            checkIfPrayed(docs?.get("Night_Zekr"), night_btn)
            checkIfPrayed(docs?.get("Sleeping_Zekr"), before_sleeping_btn)
        }

    }

    @SuppressLint("NewApi")
    fun getFBData(success: (DocumentSnapshot?) -> Unit) {
        var day: Day = weekCalendar?.selectedDay!!
        var selected = "${day.year}-0${day.month + 1}-${day.day}"
        Log.d("TAG", "$selected ")
        database.collection("App Users").document(uid).collection("Dates").document(
            selected)
            .get().addOnCompleteListener { documentSnapshot ->
                if (documentSnapshot.isSuccessful) {
                    val docs: DocumentSnapshot = documentSnapshot.result!!
                    success(docs)
                }
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIfPrayAll()
       val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        day_date.text = currentDate

        weekCalendar.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
            override fun onClickListener() {
            }

            override fun onDataUpdate() {
            }

            override fun onDayChanged() {
            }

            override fun onDaySelect() {
                checkIfPrayAll()

            }

            override fun onItemClick(v: View) {
            }

            override fun onMonthChange() {
                checkIfPrayAll()
            }

            override fun onWeekChange(position: Int) {
            }
        })


    }
}