package com.sandorln.memoapp.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sandorln.memoapp.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memo WHERE content LIKE '%' || :search || '%'")
    fun getMemoList(search: String): Flow<List<Memo>>

    @Insert(onConflict = REPLACE)
    fun insertMemo(memo: Memo)

    @Delete
    fun deleteMemo(memo: Memo)

    @Update(onConflict = REPLACE)
    fun updateMemo(memo: Memo)
}