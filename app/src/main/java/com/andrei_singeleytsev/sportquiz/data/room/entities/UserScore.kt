package com.andrei_singeleytsev.sportquiz.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_score")
data class UserScore(
    @PrimaryKey
    var id: Int? = null,
    val score: Int
)
