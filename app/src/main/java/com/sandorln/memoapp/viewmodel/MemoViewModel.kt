package com.sandorln.memoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sandorln.memoapp.usecase.CheckValidMemoPwUseCase
import com.sandorln.memoapp.usecase.GetMemoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MemoViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    private val getMemoListUseCase: GetMemoListUseCase,
    private val checkValidMemoPwUseCase: CheckValidMemoPwUseCase
) : AndroidViewModel(context as Application) {
    private val _search: MutableStateFlow<String> = MutableStateFlow("")
    fun changeSearch(search: String) = viewModelScope.launch(Dispatchers.IO) { _search.emit(search) }

    val memoList = _search
        .flatMapLatest { search -> getMemoListUseCase(search) }
        .flowOn(Dispatchers.IO)

    val memoId = savedStateHandle.get<String>("memoId") ?: ""
    suspend fun checkValidMemo(memoId: String, memoPw: String) = withContext(Dispatchers.IO) { checkValidMemoPwUseCase(memoId, memoPw) }
}