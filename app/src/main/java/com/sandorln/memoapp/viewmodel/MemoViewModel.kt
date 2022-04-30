package com.sandorln.memoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.usecase.DeleteMemoUseCase
import com.sandorln.memoapp.usecase.GetMemoListUseCase
import com.sandorln.memoapp.usecase.InsertMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getMemoListUseCase: GetMemoListUseCase,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : AndroidViewModel(context as Application) {
    private val _search: MutableStateFlow<String> = MutableStateFlow("")
    fun changeSearch(search: String) = viewModelScope.launch(Dispatchers.IO) { _search.emit(search) }
    val memoList = _search
        .transform { search -> emitAll(getMemoListUseCase(search)) }
        .flowOn(Dispatchers.IO)

    fun inputMemo(content: String) = viewModelScope.launch(Dispatchers.IO) { insertMemoUseCase(Memo(content = content)) }
    fun deleteMemo(memo: Memo) = viewModelScope.launch(Dispatchers.IO) { deleteMemoUseCase(memo) }
}