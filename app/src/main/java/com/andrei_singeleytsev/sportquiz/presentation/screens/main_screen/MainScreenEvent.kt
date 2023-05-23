package com.andrei_singeleytsev.sportquiz.presentation.screens.main_screen

sealed class MainScreenEvent(){
    data class ToGameScreen(val difficulty: Int): MainScreenEvent()
    object ToWallpaperScreen:MainScreenEvent()
}
