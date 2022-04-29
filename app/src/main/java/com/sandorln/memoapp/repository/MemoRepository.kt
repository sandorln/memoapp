package com.sandorln.memoapp.repository

import com.sandorln.memoapp.model.Memo

interface MemoRepository {
    fun getMemoList(search: String)
    fun insertMemo(memo: Memo)
    fun deleteMemo(memo: Memo)
    fun updateMemo(memo: Memo)
}