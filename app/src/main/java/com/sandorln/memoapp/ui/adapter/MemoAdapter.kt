package com.sandorln.memoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sandorln.memoapp.databinding.ItemMemoBinding
import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.ui.adapter.diff.MemoDiff

class MemoAdapter(private val onClickListener: (Memo) -> Unit) : ListAdapter<Memo, MemoAdapter.MemoViewHolder>(MemoDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder =
        MemoViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo = getItem(position)
        val isPrivateMemo = memo.pwd.isNotEmpty()
//        val context = holder.binding.root.context
//        ContextCompat.getColor(context, R.color.bg_memo_card)
        with(holder.binding) {
            imgMemoLockState.isVisible = isPrivateMemo
            card.isSelected = isPrivateMemo
            tvMemoTitle.text = if (isPrivateMemo) "비밀 메모" else memo.title
            tvMemoContent.text = if (isPrivateMemo) "" else memo.content
            root.setOnClickListener { onClickListener(memo) }
        }
    }

    class MemoViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)
}