package com.sandorln.memoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.usecase.DeleteMemoUseCase
import com.sandorln.memoapp.usecase.GetMemoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MemoViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getMemoListUseCase: GetMemoListUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : AndroidViewModel(context as Application) {
    private val _search: MutableStateFlow<String> = MutableStateFlow("")
    fun changeSearch(search: String) = viewModelScope.launch(Dispatchers.IO) { _search.emit(search) }

    val memoList = _search
        .flatMapLatest { search -> getMemoListUseCase(search) }
        .flowOn(Dispatchers.IO)

    fun deleteMemo(memo: Memo) = viewModelScope.launch(Dispatchers.IO) { deleteMemoUseCase(memo) }
}