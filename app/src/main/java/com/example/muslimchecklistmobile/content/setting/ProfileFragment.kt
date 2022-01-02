@file:Suppress("DEPRECATION")

package com.example.muslimchecklistmobile.content.setting

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class ProfileFragment : Fragment() {

    lateinit var imageUri: Uri
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    private lateinit var storageReference :StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database=FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference.child("users/"+auth.currentUser?.uid+"/profile.jpg")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Profile Data")
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid

        val uidRef = database.collection("App Users").document(uid)
        uidRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val localfile = File.createTempFile("tempImage","jpj")
                    storageReference.getFile(localfile).addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                        profile_photo.setImageBitmap(bitmap)
                        progressDialog.dismiss()
                    }.addOnFailureListener{
                        progressDialog.dismiss()
                    }
                    val profileName = document.get("Name")
                    val profileEmail = document.get("Email")
                    val profilePhone = document.get("Phone")
                    name_profile.setText(profileName.toString())
                    email_profile.setText(profileEmail.toString())
                    phone_profile.setText(profilePhone.toString())
                } else {
                    Toast.makeText(context, "Data Not Found", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }


        save_profile.setOnClickListener {
            val updateName =name_profile.text.toString()
            val updateEmail = email_profile.text.toString()
            val updatePhone = phone_profile.text.toString()
            currentUser.updateEmail(updateEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "User email address updated.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Failed to update user email address.", Toast.LENGTH_SHORT).show()
                    }
                }
            val userProfile: MutableMap<String, Any> = HashMap()
            userProfile["Name"] = updateName
            userProfile["Email"] = updateEmail
            userProfile["Phone"] = updatePhone
            userProfile["UserID"] = uid
            database.collection("App Users")
                .document(uid)
                .set(userProfile)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Profile Update Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Record Profile Update Successfully To Add",
                        Toast.LENGTH_LONG
                    )
                        .show()
            }
        }
        change_password.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
        }

        edit_profile_back.setOnClickListener {
            findNavController().popBackStack()
        }

        edit_photo_profile.setOnClickListener {
            selectImage()
        }
    }
    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
                    startActivityForResult(intent, 1)
                }
                options[item] == "Choose from Gallery" -> {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 2)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                var f = File(Environment.getExternalStorageDirectory().toString())
                for (temp in f.listFiles()) {
                    if (temp.name == "temp.jpg") {
                        f = temp
                        break
                    }
                }
                try {
                    val bitmap: Bitmap
                    val bitmapOptions = BitmapFactory.Options()
                    bitmap = BitmapFactory.decodeFile(
                        f.absolutePath,
                        bitmapOptions
                    )
                    profile_photo.setImageBitmap(bitmap)
                    val path = (Environment.getExternalStorageDirectory().toString() + File.separator + "Phoenix" + File.separator + "default")
                    f.delete()
                    val outFile: OutputStream?
                    val file = File(path, System.currentTimeMillis().toString() + ".jpg")
                    try {
                        outFile = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile)
                        outFile.flush()
                        outFile.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (requestCode == 2) {
                imageUri = data!!.data!!
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c: Cursor =
                    imageUri.let { requireActivity().contentResolver.query(
                        it,
                        filePath,
                        null,
                        null,
                        null
                    ) }!!
                c.moveToFirst()
                val columnIndex: Int = c.getColumnIndex(filePath[0])
                val picturePath: String? = c.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                profile_photo.setImageURI(imageUri)
                profile_photo.setImageBitmap(thumbnail)
                uploadImage()
            }
        }
    }
    private fun uploadImage(){
        storageReference = FirebaseStorage.getInstance().reference.child("users/"+auth.currentUser?.uid+"/profile.jpg")
         storageReference.putFile(imageUri)
             .addOnSuccessListener{
                 profile_photo.setImageURI(null)
                 Toast.makeText(context, "Image Uploaded!! ", Toast.LENGTH_SHORT).show()
             }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    Toast.makeText(context, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
        }
}