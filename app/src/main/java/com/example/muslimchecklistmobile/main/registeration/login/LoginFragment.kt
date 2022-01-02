@file:Suppress("DEPRECATION")

package com.example.muslimchecklistmobile.main.registeration.login

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.notification.*
import java.util.*


@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment2_to_tabFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        back.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_startFragment2)
        }
        forget_password.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_forgetPasswordFragment2)
        }
        register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_signUpProfileFragment2)
        }

        login_show_pass.setOnClickListener {
            if (login_show_pass.text.toString() == "ON"){
                password_login.transformationMethod=HideReturnsTransformationMethod.getInstance()
                login_show_pass.text = ""
            }else{
                password_login.transformationMethod = PasswordTransformationMethod.getInstance()
                login_show_pass.text = ""
            }
        }

        login_btn.setOnClickListener {

            val emailLogin = e_mail_login.text.toString().trim()
            val passwordLogin = password_login.text.toString().trim()

            if (TextUtils.isEmpty(emailLogin)) {
                e_mail_login.error = "Please Enter Your E mail"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordLogin)) {
                password_login.error = "Please Enter Your Password"
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Login")
            progressDialog.setMessage("Loading...")
            progressDialog.show()

            auth.fetchSignInMethodsForEmail(emailLogin).addOnCompleteListener { task ->
                        if ((task.result?.signInMethods?.size ?: 0) == 0) {
                            e_mail_login.error = "Invalid Email"
                        } else {
                            // email existed
                            auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                                .addOnCompleteListener {
                                    progressDialog.dismiss()
                                    if (it.isSuccessful){
                                        findNavController().navigate(R.id.action_loginFragment2_to_tabFragment)
                                    }else{
                                        progressDialog.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Login Failed, please try again ",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }
            }
        }
}