package com.example.flowcrud.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flowcrud.data.model.User
import com.example.flowcrud.data.network.apis.MyListener
import com.example.flowcrud.databinding.RowAllUserLayoutBinding

class AllUserAdapter constructor(private val listener: MyListener) :
    ListAdapter<User, AllUserAdapter.AllUserViewHolder>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUserViewHolder {
        return AllUserViewHolder(
            RowAllUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllUserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bindItems(user)
        }
    }


    inner class AllUserViewHolder(private val binding: RowAllUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(user: User) {
            binding.apply {
                txtId.text = user.id
                txtEmail.text = user.email
                txtUserName.text = user.username
                ivDelete.setOnClickListener {
                    listener.onClickData(adapterPosition, user.id.toInt())
                    Log.d("me", "bindItems:MM")
                }
                cvUpdate.setOnClickListener {
                    listener.onClickUpdate(
                        adapterPosition,
                        user.id.toInt(),
                        user.username,
                        user.email,
                        user.password
                    )
                }
            }
        }

    }

    object Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }


}