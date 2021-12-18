package com.ceiba.mobile.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.codapps.examen_ingreso_android.databinding.DialogLoadingBinding

class LoadingDialog : DialogFragment() {

    private lateinit var binding: DialogLoadingBinding

    private var message: String = ""

    companion object {
        private const val ARGS_MESSAGE = "ARGS_MESSAGE"

        fun newInstance(
            message: String
        ) = LoadingDialog().apply {
            arguments = Bundle().apply {
                putString(ARGS_MESSAGE, message)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoadingBinding.inflate(inflater, container, false)

        arguments?.run {
            message = this.getString(ARGS_MESSAGE, "")
        }

        binding.message = message

        // Set transparent background and no title
        if (this.dialog != null && this.dialog!!.window != null) {
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
        }

        return binding.root
    }
}