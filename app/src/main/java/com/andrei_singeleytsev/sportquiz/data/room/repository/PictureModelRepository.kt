package com.andrei_singeleytsev.sportquiz.data.room.repository

import com.andrei_singeleytsev.sportquiz.data.room.dao.PictureModelDao
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import kotlinx.coroutines.flow.Flow

interface PictureModelRepository {
    suspend fun unlockPicture(pictureModel: PictureModel)
    fun getPictures(): Flow<List<PictureModel>>
    suspend fun loadPicture(pictureModel: PictureModel)
}