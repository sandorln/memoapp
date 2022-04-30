package com.sandorln.memoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
        if (memo.pwd.isNotEmpty())
            holder.binding.tvMemo.text = "비밀 메모입니다"
        else
            holder.binding.tvMemo.text = memo.title
        holder.itemView.setOnClickListener { onClickListener(memo) }
    }

    class MemoViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)
}