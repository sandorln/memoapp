package com.sandorln.memoapp.ui.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sandorln.memoapp.R
import com.sandorln.memoapp.databinding.ActivityMemoListBinding
import com.sandorln.memoapp.ui.adapter.MemoAdapter
import com.sandorln.memoapp.ui.base.BaseActivity
import com.sandorln.memoapp.viewmodel.MemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.Serializable

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MemoListActivity : BaseActivity<ActivityMemoListBinding>(R.layout.activity_memo_list) {
    /* ViewModels */
    private val memoViewModel: MemoViewModel by viewModels()

    /* Adapters */
    private lateinit var memoAdapter: MemoAdapter

    override fun initObjectSetting() {
        memoAdapter = MemoAdapter { memo ->
            val intent = Intent(this, MemoEditorActivity::class.java).apply { putExtra("memo", memo as Serializable) }
            startActivity(intent)
        }
    }

    override fun initViewSetting() {
        binding.fabWriteMemo.setOnClickListener { startActivity(Intent(this, MemoEditorActivity::class.java)) }
        binding.rvMemo.adapter = memoAdapter
        binding.editSearch.doOnTextChanged { text, _, _, _ -> memoViewModel.changeSearch(text.toString()) }
    }

    override fun initObserverSetting() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    memoViewModel
                        .memoList
                        .collect { memoList -> memoAdapter.submitList(memoList) }
                }
            }
        }
    }
}