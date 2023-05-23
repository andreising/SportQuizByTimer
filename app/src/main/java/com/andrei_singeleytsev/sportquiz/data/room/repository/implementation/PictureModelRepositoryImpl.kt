package com.andrei_singeleytsev.sportquiz.data.room.repository.implementation

import com.andrei_singeleytsev.sportquiz.data.room.dao.PictureModelDao
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import com.andrei_singeleytsev.sportquiz.data.room.repository.PictureModelRepository
import kotlinx.coroutines.flow.Flow

class PictureModelRepositoryImpl(
    private val dao: PictureModelDao
):PictureModelRepository {
    override suspend fun unlockPicture(pictureModel: PictureModel) {
        dao.unlockPicture(pictureModel)
    }

    override fun getPictures(): Flow<List<PictureModel>> {
        return dao.getPictures()
    }

    override suspend fun loadPicture(pictureModel: PictureModel) {
        dao.loadPicture(pictureModel)
    }
}