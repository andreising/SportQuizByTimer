package com.andrei_singeleytsev.sportquiz.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockPicture(pictureModel: PictureModel)
    @Insert
    suspend fun loadPicture(pictureModel: PictureModel)
    @Query("SELECT * FROM picture_model")
    fun getPictures(): Flow<List<PictureModel>>
}