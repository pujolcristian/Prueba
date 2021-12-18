package com.ceiba.mobile.view.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.ActivityNavigator
import com.ceiba.mobile.dialogs.LoadingDialog
import com.ceiba.mobile.view.post.PostActivity
import com.ceiba.mobile.vo.Status
import com.ceiba.mobile.vo.User
import com.ceiba.examen_ingreso_android.R
import com.ceiba.examen_ingreso_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var adapter: UserListAdapter

    private var loadingDialog: LoadingDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = UserListAdapter(clickPostItemClick = { item: User, view: View ->

            val bundle = bundleOf(
                "id" to item.id,
                "name" to item.name,
                "email" to item.email,
                "phone" to item.phone
            )

            val activityNavigator = ActivityNavigator(view.context)
            activityNavigator.navigate(
                activityNavigator.createDestination().setIntent(
                    Intent(
                        view.context,
                        PostActivity::class.java
                    )
                ), bundle, null, null
            )
        })
        binding.recyclerViewSearchResults.adapter = adapter


        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val string = p0.toString()
                mainViewModel.setSearch(string)
                mainViewModel.setInitUserData(string.isEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        mainViewModel.users.observe(this, { result ->
            when (result.status) {
                Status.LOADING -> {
                    showAlertLoading()
                }
                Status.SUCCESS -> {
                    loadingDialog?.dismiss()
                    if (!result.data.isNullOrEmpty()) {
                        binding.recyclerViewSearchResults.visibility = View.VISIBLE
                        binding.relativeListEmpty.visibility = View.GONE
                        adapter.submitList(result.data)
                    } else {
                        binding.recyclerViewSearchResults.visibility = View.GONE
                        binding.relativeListEmpty.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    loadingDialog?.dismiss()
                    if (!result.data.isNullOrEmpty()) {
                        binding.recyclerViewSearchResults.visibility = View.VISIBLE
                        binding.relativeListEmpty.visibility = View.GONE
                        adapter.submitList(result.data)
                    } else {
                        binding.recyclerViewSearchResults.visibility = View.GONE
                        binding.relativeListEmpty.visibility = View.VISIBLE
                    }
                }
            }
        })
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