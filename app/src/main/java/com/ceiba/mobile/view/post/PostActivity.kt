package com.ceiba.mobile.view.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ceiba.mobile.dialogs.LoadingDialog
import com.ceiba.mobile.vo.User
import com.ceiba.mobile.vo.UserPost
import com.codapps.examen_ingreso_android.R
import com.codapps.examen_ingreso_android.databinding.ActivityPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : AppCompatActivity() {

    private val postViewModel: PostViewModel by viewModels()

    private lateinit var binding: ActivityPostBinding

    private lateinit var adapter: PostListAdapter

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding.viewModel = postViewModel
        adapter = PostListAdapter()

        val bundle = intent.extras
        if (bundle != null) {
            postViewModel.setUserData(
                user = User(
                    bundle.getInt("id"),
                    bundle.getString("name") ?: "",
                    bundle.getString("email") ?: "",
                    bundle.getString("phone") ?: ""
                )
            )

        }


        postViewModel.listUserPosts.observe(this, listPostObserver)
        postViewModel.userData.observe(this, userDataObserver)
        postViewModel.isLoading.observe(this, showDialogLoadingObserver)
    }

    private val userDataObserver = Observer<User> {
        postViewModel.getUserPost(it.id)
    }
    private val listPostObserver = Observer<MutableList<UserPost>> {
        binding.recyclerViewPostsResults.adapter = adapter
        adapter.submitList(it)
    }

    private val showDialogLoadingObserver: Observer<Boolean> = Observer { isLoading ->
        if (isLoading) {
            showAlertLoading()
        } else {
            loadingDialog?.dismiss()
        }
    }

    private fun showAlertLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.newInstance(getString(R.string.generic_message_progress))
            loadingDialog?.isCancelable = false
            if (!loadingDialog?.isAdded!!) {
                supportFragmentManager.let { loadingDialog?.show(it, "LOADING_DIALOG") }
            }
        }
    }
}