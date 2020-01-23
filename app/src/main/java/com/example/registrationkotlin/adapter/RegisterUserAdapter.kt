package com.example.registrationkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.model.RegisterUsers
import com.example.registrationkotlin.delegate.ItemListDelegate

class RegisterUserAdapter (val userList: ArrayList<RegisterUsers>, val delegate: ItemListDelegate) : RecyclerView.Adapter<RegisterUserAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterUserAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(v,delegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RegisterUserAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(
        itemView: View,
        delegate: ItemListDelegate
    ) : RecyclerView.ViewHolder(itemView) {
        private val delegate=delegate
        fun bindItems(user: RegisterUsers) {
            val liuserlist=itemView.findViewById(R.id.li_user_item_list) as LinearLayout
            val tvusername = itemView.findViewById(R.id.username_info) as TextView
            val tvphone = itemView.findViewById(R.id.phone_no_into) as TextView
            val tvemail  = itemView.findViewById(R.id.email_info) as TextView
            val tvaddress  = itemView.findViewById(R.id.address_info) as TextView

            tvusername.text = user.UserName
            tvemail.text = user.Email
            tvphone.text = user.Phone
            tvaddress.text = user.Address

            liuserlist.setOnClickListener{
                val userID=user.UserID
                delegate?.sendID(userID)
            }

        }
    }
}