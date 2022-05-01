package com.sandorln.memoapp.ui.activity

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sandorln.memoapp.R
import com.sandorln.memoapp.databinding.ActivityMemoEditorBinding
import com.sandorln.memoapp.ui.base.BaseActivity
import com.sandorln.memoapp.viewmodel.MemoEditorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoEditorActivity : BaseActivity<ActivityMemoEditorBinding>(R.layout.activity_memo_editor) {
    /* ViewModels */
    private val memoEditorViewModel: MemoEditorViewModel by viewModels()

    override fun initObjectSetting() {
    }

    override fun initViewSetting() {
        binding.editPw.doOnTextChanged { text, _, _, _ -> memoEditorViewModel.changeMemoPw(text.toString()) }
        binding.editMemoTitle.doOnTextChanged { text, _, _, _ -> memoEditorViewModel.changeMemoTitle(text.toString()) }
        binding.editMemoContent.doOnTextChanged { text, _, _, _ -> memoEditorViewModel.changeMemoContent(text.toString()) }
        binding.tvSave.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                memoEditorViewModel.saveMemo()
                finish()
                if (memoEditorViewModel.initMemo.value != null)
                    overridePendingTransition(0, 0)

                showToast("완료 되었습니다")
            }
        }
        binding.imgRollback.setOnClickListener {
            showAlterDialog("초기화", "이전 내용으로 되돌립니다", "되돌리기") {
                initMemoRollback()
                binding.root.clearFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(binding.root.windowToken, 0)
            }
        }
    }

    override fun initObserverSetting() {
        initMemoRollback()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    memoEditorViewModel.memoStringCount.collectLatest { memoStringCount ->
                        binding.tvMemoStringCount.text = memoStringCount.toString()
                    }
                }
            }
        }
    }

    private fun initMemoRollback() {
        memoEditorViewModel
            .initMemo
            .onEach { memo ->
                val hasInitMemo = memo != null
                binding.tvSave.text = if (hasInitMemo) "수정" else "저장"

                binding.imgRollback.isEnabled = hasInitMemo
                binding.imgRollback.isSelected = hasInitMemo

                binding.editMemoContent.setText(memo?.content ?: "")
                binding.editMemoTitle.setText(memo?.title ?: "")
                binding.editPw.setText(memo?.pwd ?: "")
            }.launchIn(lifecycleScope)
    }
}