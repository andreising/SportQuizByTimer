package com.andrei_singeleytsev.sportquiz.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "picture_model")
data class PictureModel(
    @PrimaryKey
    val id: Int? = null,
    val pictureId: Int,
    val coast: Int,
    val isEnabled: Boolean = false
)