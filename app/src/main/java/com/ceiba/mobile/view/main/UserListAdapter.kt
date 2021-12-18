package com.ceiba.mobile.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ceiba.mobile.utils.DataBoundListAdapter
import com.ceiba.mobile.vo.User
import com.codapps.examen_ingreso_android.R
import com.codapps.examen_ingreso_android.databinding.UserListItemBinding

class UserListAdapter(
    private val clickPostItemClick: (item: User, view: View) -> Unit
) :
    DataBoundListAdapter<User, UserListItemBinding>(
        UserDiffCallback()
    ) {
    override fun createBinding(parent: ViewGroup): UserListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent,
            false
        )
    }


    override fun bind(binding: UserListItemBinding, item: User) {
        binding.user = item

        binding.setOnClickPost {
            clickPostItemClick.invoke(item, it)
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.email == newItem.email
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.email == newItem.email
        }
    }
}