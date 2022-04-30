package com.sandorln.memoapp.di

import com.sandorln.memoapp.repository.MemoRepository
import com.sandorln.memoapp.repository.MemoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsMemoRepository(memoRepositoryImpl: MemoRepositoryImpl): MemoRepository
}