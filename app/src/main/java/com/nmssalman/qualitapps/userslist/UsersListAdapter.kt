package com.nmssalman.qualitapps.userslist

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nmssalman.qualitapps.databinding.ItemUsersListBinding
import com.nmssalman.qualitapps.userslist.dataset.User

class UsersListAdapter(private var users: List<User>): RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return users.count()
    }

    fun updateUsers(users: List<User>){
        this.users = users
    }
    class ViewHolder(val binding: ItemUsersListBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat", "DefaultLocale")
        fun bind(user: User) {
            with(binding){
                name.text = user.name
                address.text = "${user.address.street}\n${user.address.suite}\n${user.address.city}"
                email.text = user.email
                phone.text = user.phone
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUsersListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}