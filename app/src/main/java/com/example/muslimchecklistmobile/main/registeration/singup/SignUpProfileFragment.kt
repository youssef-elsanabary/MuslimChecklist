package com.example.muslimchecklistmobile.main.registeration.singup

import android.os.Bundle
import android.text.TextUtils
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
import kotlinx.android.synthetic.main.fragment_sign_up_profile.*
import kotlinx.android.synthetic.main.fragment_sign_up_profile.register
import java.text.SimpleDateFormat
import java.util.*


class SignUpProfileFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database=FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profile_back.setOnClickListener {
            findNavController().navigate(R.id.action_signUpProfileFragment2_to_loginFragment2)
        }

        signin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpProfileFragment2_to_loginFragment2)
        }

        register.setOnClickListener {
            val firstName = first_name.text.toString().trim()
            val lastName = last_name.text.toString().trim()
            val email =e_mail.text.toString().trim()
            val password = pass.text.toString().trim()
            val phone = phoneNumber.text.toString().trim()
            val name = "$firstName $lastName"
            val date = SimpleDateFormat("yyyy,dd,MMM", Locale.getDefault()).format(Date())
            val time: String = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
            val loginDate = date + time// email existed


            //Create Account
            when {
                TextUtils.isEmpty(firstName) -> {
                    first_name.error = "Enter You Name"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(lastName) -> {
                    last_name.error = "Enter You Name"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(phone) -> {
                    last_name.error = "Enter You Phone Number"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(email) -> {
                    e_mail.error = "Enter You Name"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(password) -> {
                    pass.error = "Enter You Name"
                    return@setOnClickListener
                }
                //Check Email
                else -> auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                    if ((task.result?.signInMethods?.size) == 0) {
                        //Create Account
                        auth.createUserWithEmailAndPassword(
                            e_mail.text.toString().trim(),
                            pass.text.toString().trim()
                        )
                            .addOnCompleteListener {
                                val currentUser = auth.currentUser
                                if (it.isSuccessful) {
                                    val uid = currentUser!!.uid
                                    val userProfile: MutableMap<String, Any> = HashMap()
                                    userProfile["Name"] = name
                                    userProfile["Email"] = email
                                    userProfile["Phone"] = phone
                                    userProfile["Registration Date"] = date
                                    userProfile["Login Date"] = loginDate
                                    userProfile["UserID"] = uid

                                    database.collection("App Users")
                                        .document(uid)
                                        .set(userProfile)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,
                                                "Record Added Successfully",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            findNavController().navigate(R.id.action_signUpProfileFragment2_to_loginFragment2)
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context,
                                                "Record Failed To Add",
                                                Toast.LENGTH_LONG)
                                                .show()
                                        }
                                } else {
                                    Toast.makeText(context,
                                        "Sign Up Failed ,Please Try Again",
                                        Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                    } else {
                        // email existed
                        e_mail.error = "This Email Already Exist"
                    }
                }
            }
        }
        }
}