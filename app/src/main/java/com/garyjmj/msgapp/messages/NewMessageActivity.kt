package com.garyjmj.msgapp

import android.content.ClipData
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration.get
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.appcompat.widget.ResourceManagerInternal.get
import androidx.recyclerview.widget.RecyclerView
import com.garyjmj.msgapp.messages.ChatLogActivity
import com.garyjmj.msgapp.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import kotlin.coroutines.EmptyCoroutineContext.get

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"

//    val adapter = GroupAdapter<ViewHolder>()
//
//    adapter.add(UserItem())
//    adapter.add(UserItem())
//    adapter.add(UserItem())
//
//    recyclerview_newmessage.adapter = adapter

        fetchUsers()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    //intent.putExtra(USER_KEY,  userItem.user.username)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()

                }

                recyclerview_newmessage.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_new_message)

    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}


// this is super tedious

//class CustomAdapter: RecyclerView.Adapter<ViewHolder> {
//  override fun onBindViewHolder(p0:, p1: Int) {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//  }
//}