package com.sandorln.memoapp.usecase

import com.sandorln.memoapp.repository.MemoRepository
import javax.inject.Inject

class GetMemoListUseCase @Inject constructor(private val memoRepository: MemoRepository) {
    operator fun invoke(search: String) =
        memoRepository.getMemoList(search)
}