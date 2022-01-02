package com.example.muslimchecklistmobile.main.registeration.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgetPasswordFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forget_back.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPasswordFragment2_to_loginFragment2)
        }
        forget_btn.setOnClickListener {
            val email =e_mail_forget.text.toString().trim()
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context,"password reset email successfully",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Failed to send password reset email",Toast.LENGTH_LONG).show()
                }
            }
            findNavController().navigate(R.id.action_forgetPasswordFragment2_to_loginFragment2)
        }
    }
}