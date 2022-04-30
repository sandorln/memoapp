package com.sandorln.memoapp.repository

import com.sandorln.memoapp.database.dao.MemoDao
import com.sandorln.memoapp.model.Memo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoDao: MemoDao
) : MemoRepository {
    override fun getMemoList(search: String): Flow<List<Memo>> = memoDao.getMemoList(search)

    override suspend fun insertMemo(memo: Memo) = memoDao.insertMemo(memo)
    override suspend fun deleteMemo(memo: Memo) = memoDao.deleteMemo(memo)
    override suspend fun updateMemo(memo: Memo) = memoDao.updateMemo(memo)
}