package com.sandorln.memoapp.di

import com.sandorln.memoapp.repository.MemoRepository
import com.sandorln.memoapp.usecase.DeleteMemoUseCase
import com.sandorln.memoapp.usecase.GetMemoListUseCase
import com.sandorln.memoapp.usecase.InsertMemoUseCase
import com.sandorln.memoapp.usecase.UpdateMemoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun providesGetMemoListUseCase(memoRepository: MemoRepository) = GetMemoListUseCase(memoRepository)

    @Provides
    fun providesInsertMemoUseCase(memoRepository: MemoRepository) = InsertMemoUseCase(memoRepository)

    @Provides
    fun providesDeleteMemoUseCase(memoRepository: MemoRepository) = DeleteMemoUseCase(memoRepository)

    @Provides
    fun providesUpdateMemoUseCase(memoRepository: MemoRepository) = UpdateMemoUseCase(memoRepository)
}