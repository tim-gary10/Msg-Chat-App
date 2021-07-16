package com.garyjmj.msgapp.registerlogin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.garyjmj.msgapp.R
import com.garyjmj.msgapp.messages.LatestMessagesActivity
import com.garyjmj.msgapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import kotlin.math.log

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImageUri : Uri
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var firebaseuser: FirebaseUser


    companion object {
        var currentUser: User? = null
        val TAG = "Profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profile_save_button.setOnClickListener {
            val changeName = profile_editText_name.text.toString()

            changeProfile(changeName)

        }

    }

    private fun changeProfile(changeName: String) {

        database = FirebaseDatabase.getInstance().getReference("users")
        val user = mapOf<String, String>(
                "username" to changeName

        )

        database.child(changeName).updateChildren(user).addOnSuccessListener {
            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
        }


    }
}
//binding.updateBtn.setOnClickListener {
//
//    val userName = binding.userName.text.toString()
//    val firstName = binding.firstName.text.toString()
//    val lastName = binding.lastname.text.toString()
//    val age = binding.age.text.toString()
//
//    updateData(userName,firstName,lastName,age)
//
//}
//
//}

//private fun updateData(userName: String, firstName: String, lastName: String, age: String) {
//
//    database = FirebaseDatabase.getInstance().getReference("Users")
//    val user = mapOf<String,String>(
//            "firstName" to firstName,
//            "lastName" to lastName,
//            "age" to age
//    )
//
//    database.child(userName).updateChildren(user).addOnSuccessListener {
//
//        binding.userName.text.clear()
//        binding.firstName.text.clear()
//        binding.lastname.text.clear()
//        binding.age.text.clear()
//        Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()
//
//
//    }.addOnFailureListener{
//
//        Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
//
//    }}




























//        auth = FirebaseAuth.getInstance()
//        val user  = auth.currentUser
//
//        if (user != null){
//            if (user.photoUrl != null){
//                Picasso.get().load(user.photoUrl).into(profile_image)
//            }else{
//                Picasso.get().load("gs://msg-chat-app.appspot.com/images/goldfish_PNG45.png").into(profile_image)
//            }
//        }
//
//        profile_editText_name.setText(user?.displayName)

//        profile_save_button.setOnClickListener {
//
//            val changeProfileName = profile_editText_name.text.toString()
//
//            updatedData(changeProfileName)
//
////            val image = when{
////                ::profileImageUri.isInitialized -> profileImageUri
////                user?.photoUrl == null -> Uri.parse("gs://msg-chat-app.appspot.com/images/goldfish_PNG45.png")
////                else -> user.photoUrl
////            }
////
////            val name = profile_editText_name.text.toString().trim()
////
////            if (name.isEmpty()){
////                profile_editText_name.error = "error"
////                profile_editText_name.requestFocus()
////                return@setOnClickListener
////            }
////            UserProfileChangeRequest.Builder()
////                    .setDisplayName(name)
////                    .setPhotoUri(image)
////                    .build().also {
////                        user?.updateProfile(it)?.addOnCompleteListener{
////                            if (it.isSuccessful){
////                                Toast.makeText(this,  "profile upload", Toast.LENGTH_SHORT).show()
////                            }else{
////                                Toast.makeText(this, "profile not upload", Toast.LENGTH_SHORT).show()
////                            }
////                        }
////                    }
//        }
//
//
//        profile_image.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, 0)
//        }
//
//    }
//
//    private fun updatedData(changeProfileName: String) {
//        database = FirebaseDatabase.getInstance().getReference("Users")
//        val user = mapOf<String, String>(
//                "username" to changeProfileName
//        )
//
//        database.child(changeProfileName).updateChildren(user).addOnSuccessListener {
//            profile_editText_name.text.clear()
//
//        }.addOnFailureListener {
//            Toast.makeText(this,"failed to upload", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
//
//
//            profileImageUri = data.data!!
//
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, profileImageUri)
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            profile_image.setBackgroundDrawable(bitmapDrawable)
////            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, profileImageUri)
////            profile_image.setImageBitmap(bitmap)
////            profile_image.alpha = 0f
//        }
//    }
//
//    private fun upLoadImageToFirebase() {
//        if (profileImageUri == null) return
//
//        val filename = UUID.randomUUID().toString()
//        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//
//        ref.putFile(profileImageUri!!)
//            .addOnSuccessListener {
//                Log.d(ProfileActivity.TAG, "Successfully uploaded image: ${it.metadata?.path}")
//            }
//
//    }