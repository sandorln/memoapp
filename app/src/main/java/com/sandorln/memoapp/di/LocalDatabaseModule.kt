package com.sandorln.memoapp.di

import android.content.Context
import androidx.room.Room
import com.sandorln.memoapp.database.AppDatabase
import com.sandorln.memoapp.database.dao.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "memoapp")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesMemoDao(appDatabase: AppDatabase): MemoDao = appDatabase.memoDao()
}