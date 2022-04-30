package com.sandorln.memoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    @PrimaryKey
    var id: Int = 0,
    val content: String,
    val pwd: String = ""
)