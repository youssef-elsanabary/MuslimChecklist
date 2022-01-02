@file:Suppress("DEPRECATION")

package com.example.muslimchecklistmobile.content.setting

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.muslimchecklistmobile.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_setting.*
import java.io.File


@Suppress("DEPRECATION")
class SettingFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    private lateinit var storageReference : StorageReference
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
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Setting")
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        storageReference = FirebaseStorage.getInstance().reference.child("users/"+auth.currentUser?.uid+"/profile.jpg")
        val localfile = File.createTempFile("tempImage","jpj")
        storageReference.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            setting_image?.setImageBitmap(bitmap)
            progressDialog.dismiss()
        }.addOnFailureListener{
            progressDialog.dismiss()
        }
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid

        val uidRef = database.collection("App Users").document(uid)
        uidRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val profileName = document.get("Name")
                    val profileEmail = document.get("Email")
                    setting_name?.text = profileName.toString()
                    setting_email?.text = profileEmail.toString()
                } else {
                    Toast.makeText(context,"Data Not Found",Toast.LENGTH_LONG).show()
                }
            }

        info_btn.setOnClickListener {
            findNavController().navigate(R.id.action_tabFragment_to_profileFragment2)
        }
        language_btn.setOnClickListener {
            findNavController().navigate(R.id.action_tabFragment_to_languageFragment2)
        }
        contact_btn.setOnClickListener {
            findNavController().navigate(R.id.action_tabFragment_to_helpFragment2)
        }
        about_btn.setOnClickListener {
            findNavController().navigate(R.id.action_tabFragment_to_aboutFragment2)
        }
        logout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_tabFragment_to_loginFragment2)
        }
        share_app_txt.setOnClickListener {

            val shareSub = "MuslimCheckListApp"
            val shareBody = "youssefelsanabary40@yahoo.com"

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "type/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub)
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody)
            startActivity(shareIntent)
        }

    }}