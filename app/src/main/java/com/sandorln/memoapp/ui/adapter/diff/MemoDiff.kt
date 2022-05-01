package com.sandorln.memoapp.ui.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.sandorln.memoapp.model.Memo

class MemoDiff : DiffUtil.ItemCallback<Memo>() {
    override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean = oldItem == newItem
}