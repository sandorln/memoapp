package com.sandorln.memoapp.repository

import com.sandorln.memoapp.model.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun getMemoList(search: String) : Flow<List<Memo>>
    suspend fun insertMemo(memo: Memo)
    suspend fun deleteMemo(memo: Memo)
    suspend fun updateMemo(memo: Memo)
}