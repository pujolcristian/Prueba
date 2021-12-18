package com.ceiba.mobile.view.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ceiba.mobile.utils.DataBoundListAdapter
import com.ceiba.mobile.vo.UserPost
import com.ceiba.examen_ingreso_android.R
import com.ceiba.examen_ingreso_android.databinding.PostListItemBinding

class PostListAdapter() :
    DataBoundListAdapter<UserPost, PostListItemBinding>(
        UserPostDiffCallback()
    ) {
    override fun createBinding(parent: ViewGroup): PostListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.post_list_item,
            parent,
            false
        )
    }


    override fun bind(binding: PostListItemBinding, item: UserPost) {
        binding.userPost = item

    }

    private class UserPostDiffCallback : DiffUtil.ItemCallback<UserPost>() {
        override fun areItemsTheSame(
            oldItem: UserPost,
            newItem: UserPost
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.body == newItem.body
        }

        override fun areContentsTheSame(
            oldItem: UserPost,
            newItem: UserPost
        ): Boolean {
            return  oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.body == newItem.body
        }
    }
}