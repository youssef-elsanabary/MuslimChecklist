package com.example.muslimchecklistmobile.content.home

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslimchecklistmobile.R
import com.example.muslimchecklistmobile.content.home.other.OtherAdapter
import com.example.muslimchecklistmobile.content.home.prayers.PrayersAdapter
import com.example.muslimchecklistmobile.content.home.supplications.SupplicationAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), PrayersAdapter.PrayersAdapterClickListener,
    SupplicationAdapter.SupplicationAdapterClickListener, OtherAdapter.OtherAdapterClickListener {

    val currentDate: String =
        SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
    private val currentTime: String = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //SET PRAYERS ADAPTER
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        prayers_recycler.layoutManager = layoutManager
        val pAdapter = PrayersAdapter(this)
        prayers_recycler.adapter = pAdapter
        //SET SUPPLICATIONS ADAPTER
        val layoutManagerSupp: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        supplications_recycler.layoutManager = layoutManagerSupp
        supplications_recycler.adapter = SupplicationAdapter(this)
        //SET OTHER ADAPTER
        val layoutManagerOther: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        other_recycler.layoutManager = layoutManagerOther
        other_recycler.adapter = OtherAdapter(this)
        //PROGRESS BAR
        home_progress.progressTintList = ColorStateList.valueOf(Color.WHITE)
        database.collection("App Users").document(uid).collection("Dates").document(currentDate)
        .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        val map = document.data
                        val progressCount = map!!.size
                        val homeProgress = home_progress
                        if (homeProgress != null){
                            homeProgress.progress = progressCount
                            val progressValue = homeProgress.progress
                            progress_home_text?.text = progressValue.toString()
                        }else
                        {
                            homeProgress?.progress = 0
                            val progressValue = homeProgress?.progress
                            progress_home_text?.text = progressValue.toString()
                        }
                    }
                }
            }
        date.text = currentDate
        time.text = currentTime
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onItemClick(position: Int) {
        when (position) {
            5 -> {
                findNavController().navigate(R.id.action_tabFragment_to_fajrFragment)
            }
            4 -> {
                findNavController().navigate(R.id.action_tabFragment_to_duhaFragment)
            }
            3 -> {
                findNavController().navigate(R.id.action_tabFragment_to_zhrFragment)
            }
            2 -> {
                findNavController().navigate(R.id.action_tabFragment_to_asrFragment)
            }
            1 -> {
                findNavController().navigate(R.id.action_tabFragment_to_maghrebFragment)
            }
            0 -> {
                findNavController().navigate(R.id.action_tabFragment_to_ishaFragment)
            }
        }

    }

    override fun onSupplicationClick(position: Int) {
        when (position) {
            0 -> {
                findNavController().navigate(R.id.action_tabFragment_to_beforeFragment)
            }
            1 -> {
                findNavController().navigate(R.id.action_tabFragment_to_nightFragment)
            }
            2 -> {
                findNavController().navigate(R.id.action_tabFragment_to_morningFragment)
            }
        }
    }

    override fun onOtherClick(position: Int) {
        when (position) {
            0 -> {
                // val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("هل انت صائم اليوم")
                builder.setPositiveButton("نعم") { _, _ ->

                }.setNegativeButton("لا") { _, _ ->

                }
                builder.show()
            }
            1 -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("هل قمت بالوضوء اليوم")
                builder.setPositiveButton("نعم") { _, _ ->

                }.setNegativeButton("لا") { _, _ ->

                }
                builder.show()
            }
            2 -> {
                val options = arrayOf<CharSequence>("قراءة القرآن", "حفظ القرآن")
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("قمت اليوم ب..؟")
                builder.setItems(options) { _, item ->
                    when {
                        options[item] == "قراءة القرآن" -> {
                        }
                        options[item] == "حفظ القرآن" -> {
                        }
                    }
                }
                builder.show()
            }
        }
    }}