package com.sandorln.memoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.sandorln.memoapp.model.Memo
import com.sandorln.memoapp.usecase.InsertMemoUseCase
import com.sandorln.memoapp.usecase.UpdateMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemoEditorViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    private val insertMemoUseCase: InsertMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
) : AndroidViewModel(context as Application) {
    suspend fun saveMemo() = withContext(Dispatchers.IO) {
        val title = _memoTitle.first()
        val content = _memoContent.first()
        val pwd = _memoPw.first()

        val memo = initMemo.firstOrNull()
        if (memo == null)
            insertMemoUseCase(Memo(title = title, content = content, pwd = pwd))
        else
            updateMemoUseCase(memo.copy(title = title, content = content, pwd = pwd))
    }

    val initMemo = savedStateHandle.getLiveData<Memo>("memo").asFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
    private val _memoTitle: MutableStateFlow<String> = MutableStateFlow("")
    fun changeMemoTitle(memoTitle: String) = viewModelScope.launch(Dispatchers.IO) { _memoTitle.emit(memoTitle) }
    private val _memoContent: MutableStateFlow<String> = MutableStateFlow("")
    fun changeMemoContent(memoContent: String) = viewModelScope.launch(Dispatchers.IO) { _memoContent.emit(memoContent) }
    val memoStringCount = _memoContent.map { it.length }

    private val _memoPw: MutableStateFlow<String> = MutableStateFlow("")
    fun changeMemoPw(memoPw: String) = viewModelScope.launch(Dispatchers.IO) { _memoPw.emit(memoPw) }
}