package com.andrei_singeleytsev.sportquiz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andrei_singeleytsev.sportquiz.data.room.dao.PictureModelDao
import com.andrei_singeleytsev.sportquiz.data.room.dao.UserScoreDao
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore

@Database(
    entities = [PictureModel::class, UserScore::class],
    version = 1
)
abstract class MainDataBase: RoomDatabase() {
    abstract val pictureModelDao:PictureModelDao
    abstract val userScoreDao: UserScoreDao
}