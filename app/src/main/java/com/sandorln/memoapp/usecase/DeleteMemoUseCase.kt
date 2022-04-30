package com.sandorln.memoapp.usecase

import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.repository.MemoRepository
import javax.inject.Inject

class DeleteMemoUseCase @Inject constructor(private val memoRepository: MemoRepository) {
    suspend operator fun invoke(memo: Memo) = memoRepository.deleteMemo(memo)
}