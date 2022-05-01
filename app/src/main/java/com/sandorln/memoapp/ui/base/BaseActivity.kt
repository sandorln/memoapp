package com.sandorln.memoapp.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initObjectSetting()
        initViewSetting()
        initObserverSetting()
    }

    abstract fun initObjectSetting()
    abstract fun initViewSetting()
    abstract fun initObserverSetting()

    fun showAlterDialog(title: String, message: String, okBtnName: String, onSubmitListener: () -> Unit) =
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okBtnName) { dialog, _ ->
                onSubmitListener()
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
}