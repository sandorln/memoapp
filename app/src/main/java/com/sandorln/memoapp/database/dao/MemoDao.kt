package com.sandorln.memoapp.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sandorln.memoapp.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memo WHERE content LIKE '%' || :search || '%' ORDER BY id DESC")
    fun getMemoList(search: String): Flow<List<Memo>>

    @Insert(onConflict = REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)
}