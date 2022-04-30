package com.sandorln.memoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.sandorln.memoapp.usecase.GetMemoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    getMemoByIdUseCase: GetMemoByIdUseCase
) : AndroidViewModel(context as Application) {
    val memo = getMemoByIdUseCase(savedStateHandle.get<String>("memoId") ?: "")
}