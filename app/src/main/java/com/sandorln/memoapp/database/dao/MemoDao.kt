package com.sandorln.memoapp.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sandorln.memoapp.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memo WHERE content LIKE '%' || :search || '%' OR title LIKE '%' || :search || '%' ORDER BY id DESC")
    fun getMemoList(search: String): Flow<List<Memo>>

    @Insert(onConflict = REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)

    @Query("SELECT * FROM Memo WHERE id == :id")
    fun getMemoById(id: String): Memo

    @Query("SELECT * FROM Memo WHERE id == :id AND pwd == :pw")
    fun checkValidMemoPw(id: String, pw: String) : Memo?
}