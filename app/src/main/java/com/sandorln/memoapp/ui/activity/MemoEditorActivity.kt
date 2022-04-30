package com.sandorln.memoapp.ui.activity

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
import kotlinx.coroutines.flow.*
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
        binding.tvSave.setOnClickListener { memoEditorViewModel.saveMemo() }
    }

    override fun initObserverSetting() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    memoEditorViewModel.memoStringCount.collectLatest { memoStringCount ->
                        binding.tvMemoStringCount.text = memoStringCount.toString()
                    }
                }

                launch {
                    memoEditorViewModel
                        .initMemo
                        .filter { it != null }
                        .map { it!! }
                        .onEach { memo ->
                            binding.tvSave.text = "수정"
                            binding.editMemoContent.setText(memo.content)
                            binding.editMemoTitle.setText(memo.title)
                            binding.editPw.setText(memo.pwd)
                        }
                        .launchIn(lifecycleScope)
                }
            }
        }
    }
}