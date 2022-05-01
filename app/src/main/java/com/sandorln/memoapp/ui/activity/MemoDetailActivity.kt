package com.sandorln.memoapp.ui.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sandorln.memoapp.R
import com.sandorln.memoapp.databinding.ActivityMemoDetailBinding
import com.sandorln.memoapp.model.BundleKey
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
                val intent = Intent(this@MemoDetailActivity, MemoEditorActivity::class.java).apply { putExtra(BundleKey.MEMO, memo as Serializable) }
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
        binding.tvDelete.setOnClickListener {
            showAlterDialog("삭제", "주의 : 삭제 시 다시 복원할 수 없습니다", "삭제") {
                lifecycleScope.launchWhenResumed {
                    memoDetailViewModel.deleteMemo()
                    finish()
                }
            }
        }
    }

    override fun initObserverSetting() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                memoDetailViewModel
                    .memo
                    .catch {
                        showToast("해당 메모를 찾을 수 없습니다")
                        finish()
                    }
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