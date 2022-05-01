package com.sandorln.memoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.sandorln.memoapp.usecase.DeleteMemoUseCase
import com.sandorln.memoapp.usecase.GetMemoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    getMemoByIdUseCase: GetMemoByIdUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : AndroidViewModel(context as Application) {
    val memo = getMemoByIdUseCase(savedStateHandle.get<String>("memoId") ?: "")
    suspend fun deleteMemo() = deleteMemoUseCase(memo = memo.first())
}