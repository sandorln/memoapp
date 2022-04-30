package com.sandorln.memoapp.ui.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sandorln.memoapp.R
import com.sandorln.memoapp.databinding.ActivityMemoDetailBinding
import com.sandorln.memoapp.ui.base.BaseActivity
import com.sandorln.memoapp.viewmodel.MemoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.Serializable

@AndroidEntryPoint
class MemoDetailActivity : BaseActivity<ActivityMemoDetailBinding>(R.layout.activity_memo_detail) {
    /* ViewModels */
    private val memoDetailViewModel: MemoDetailViewModel by viewModels()

    override fun initObjectSetting() {
    }

    override fun initViewSetting() {
        binding.tvEditor.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                val memo = memoDetailViewModel.memo.firstOrNull()
                val intent = Intent(this@MemoDetailActivity, MemoEditorActivity::class.java).apply { putExtra("memo", memo as Serializable) }
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
    }

    override fun initObserverSetting() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                memoDetailViewModel
                    .memo
                    .catch { Toast.makeText(this@MemoDetailActivity, "해당 메모를 찾을 수 없습니다", Toast.LENGTH_SHORT).show() }
                    .collectLatest { memo ->
                        with(binding) {
                            imgMemoLockState.isSelected = memo.pwd.isNotEmpty()
                            tvMemoContent.text = memo.content
                            tvMemoTitle.text = memo.title
                        }
                    }
            }
        }
    }
}