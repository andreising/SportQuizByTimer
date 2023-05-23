package com.andrei_singeleytsev.sportquiz.presentation.screens.wallpapers_screen

import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel

sealed class WallpapersScreenEvent {
    data class MainButtonPressed(val pictureModel: PictureModel): WallpapersScreenEvent()
}