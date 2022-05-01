package com.sandorln.memoapp.usecase

import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.repository.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(memoId: String): Flow<Memo> = flow { emit(memoRepository.getMemoById(memoId)) }.flowOn(Dispatchers.IO)
}