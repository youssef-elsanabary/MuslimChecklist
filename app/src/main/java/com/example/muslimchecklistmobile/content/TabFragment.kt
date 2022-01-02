package com.example.muslimchecklistmobile.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.muslimchecklistmobile.R
import com.example.muslimchecklistmobile.content.days.DayFragment
import com.example.muslimchecklistmobile.content.home.HomeFragment
import com.example.muslimchecklistmobile.content.setting.SettingFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_tab.*
import java.text.SimpleDateFormat
import java.util.*

class TabFragment : Fragment() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser
    var uid = currentUser!!.uid
    lateinit var viewPager2 : ViewPager2
    lateinit var tabLayout: TabLayout
    var tabTitle = arrayOf("الرئيسية","التقارير","اليوم","الاعدادات")
    var tabIcon = arrayOf(R.drawable.home_,R.drawable.reports_,R.drawable.today_,R.drawable.settings_)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate: String =
            SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())

        val login_date = "$currentDate $currentTime"

        val loginDate: MutableMap<String, Any> = HashMap()
        loginDate["Login Date"] = login_date

        database.collection("App Users")
            .document(uid)
            .update(loginDate)




        val firstFragment=HomeFragment()
        val secondFragment=DayFragment()
        //val thirdFragment=ReportsFragment()
        val forthFragment=SettingFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->setCurrentFragment(firstFragment)
                R.id.menu_day->setCurrentFragment(secondFragment)
                //R.id.menu_reports->setCurrentFragment(thirdFragment)
                R.id.menu_settings->setCurrentFragment(forthFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment)=


        childFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}