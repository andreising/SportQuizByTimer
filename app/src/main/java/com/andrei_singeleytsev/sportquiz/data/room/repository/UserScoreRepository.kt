package com.andrei_singeleytsev.sportquiz.data.room.repository


import com.andrei_singeleytsev.sportquiz.data.room.dao.UserScoreDao
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import kotlinx.coroutines.flow.Flow


interface UserScoreRepository {
    suspend fun insertItem(userScore: UserScore)
    fun getScore(id: Int): Flow<UserScore>
}