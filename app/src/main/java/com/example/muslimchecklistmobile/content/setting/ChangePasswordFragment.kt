package com.example.muslimchecklistmobile.content.setting

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (show_new_password.text.toString() == "ON"){
            new_password.transformationMethod= HideReturnsTransformationMethod.getInstance()
            show_new_password.text = ""
        }else{
            new_password.transformationMethod = PasswordTransformationMethod.getInstance()
            show_new_password.text = ""
        }

        if (show_verfied_password.text.toString() == "ON"){
            verfiy_password.transformationMethod= HideReturnsTransformationMethod.getInstance()
            show_new_password.text = ""
        }else{
            show_verfied_password.transformationMethod = PasswordTransformationMethod.getInstance()
            show_new_password.text = ""
        }

        change_back.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)
        }
        change.setOnClickListener {
            val txtNewPass = new_password.text
            val txtConfirmPass = verfiy_password.text
            if (txtConfirmPass == txtNewPass){
                currentUser!!.updatePassword(txtNewPass.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Update Success")
                    } else {
                        println("Error Update")
                    }
                }
            }else {
                verfiy_password.error = "Passwords aren't the same"
            }
        }
    }
}