package com.sandorln.memoapp.di

import com.sandorln.memoapp.repository.MemoRepository
import com.sandorln.memoapp.usecase.*
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

    @Provides
    fun providesCheckValidMemoPwUseCase(memoRepository: MemoRepository) = CheckValidMemoPwUseCase(memoRepository)
}