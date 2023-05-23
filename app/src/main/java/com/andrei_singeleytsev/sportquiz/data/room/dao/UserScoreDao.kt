package com.andrei_singeleytsev.sportquiz.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import kotlinx.coroutines.flow.Flow

@Dao
interface UserScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(userScore: UserScore)
    @Query ("SELECT * FROM user_score WHERE :id")
    fun getScore(id: Int): Flow<UserScore>
}