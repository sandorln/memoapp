package com.sandorln.memoapp.ui.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sandorln.memoapp.application.MemoAppApplication
import com.sandorln.memoapp.databinding.DialogCheckMemoPwValidBinding
import com.sandorln.memoapp.ui.activity.MemoDetailActivity
import com.sandorln.memoapp.viewmodel.MemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CheckMemoPwValidDialog : DialogFragment() {
    /* ViewModels */
    private val memoViewModel: MemoViewModel by viewModels()

    lateinit var binding: DialogCheckMemoPwValidBinding

    companion object {
        fun newInstance(memoId: String): CheckMemoPwValidDialog {
            val args = Bundle().apply { putString("memoId", memoId) }
            return CheckMemoPwValidDialog().apply { arguments = args }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogCheckMemoPwValidBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.editPw.setOnEditorActionListener { _, code, _ ->
            if (code == EditorInfo.IME_ACTION_DONE)
                checkValidMemo()

            true
        }
        binding.tvConfirm.setOnClickListener { checkValidMemo() }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun checkValidMemo() {
        lifecycleScope.launchWhenResumed {
            val memoId = memoViewModel.memoId
            val isValidPw = memoViewModel.checkValidMemo(memoId, binding.editPw.text.toString())
            if (isValidPw) {
                val intent = Intent(requireContext(), MemoDetailActivity::class.java).apply { putExtra("memoId", memoId) }
                startActivity(intent)
            } else {
                (requireContext().applicationContext as? MemoAppApplication)?.showToast("비밀번호가 틀렸습니다")
            }
            dialog?.dismiss()
        }
    }
}