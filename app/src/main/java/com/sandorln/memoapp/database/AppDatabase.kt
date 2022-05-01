package com.sandorln.memoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sandorln.memoapp.database.dao.MemoDao
import com.sandorln.memoapp.model.Memo

@Database(entities = [Memo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}