package com.sandorln.memoapp.usecase

import com.sandorln.memoapp.repository.MemoRepository
import javax.inject.Inject

class CheckValidMemoPwUseCase @Inject constructor(private val memoRepository: MemoRepository) {
    suspend operator fun invoke(memoId: String, memoPw: String) = memoRepository.checkValidMemoPw(memoId, memoPw)
}