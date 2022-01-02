package com.example.muslimchecklistmobile.main.splash

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import kotlinx.android.synthetic.main.fragment_start.*


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            start_btn.setOnClickListener {
                val progressDialog = ProgressDialog(context)
                progressDialog.setTitle("Profile Data")
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            findNavController().navigate(R.id.action_startFragment2_to_loginFragment2)
                progressDialog.dismiss()
        }
    }
}