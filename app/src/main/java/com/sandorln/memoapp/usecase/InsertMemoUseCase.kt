package com.sandorln.memoapp.usecase

import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.repository.MemoRepository
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(private val memoRepository: MemoRepository) {
    suspend operator fun invoke(memo: Memo) = memoRepository.insertMemo(memo)
}