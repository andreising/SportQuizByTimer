package com.andrei_singeleytsev.sportquiz.data.room.repository.implementation

import com.andrei_singeleytsev.sportquiz.data.room.dao.UserScoreDao
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import com.andrei_singeleytsev.sportquiz.data.room.repository.UserScoreRepository
import kotlinx.coroutines.flow.Flow


class UserScoreRepositoryImpl(
    private val dao: UserScoreDao
):UserScoreRepository {
    override suspend fun insertItem(userScore: UserScore) {
        dao.insertItem(userScore)
    }

    override fun getScore(id: Int): Flow<UserScore> {
        return dao.getScore(id)
    }

}