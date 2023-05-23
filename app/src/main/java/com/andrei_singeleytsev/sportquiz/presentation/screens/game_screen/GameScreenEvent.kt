package com.andrei_singeleytsev.sportquiz.presentation.screens.game_screen

sealed class GameScreenEvent(){
    data class SendAnswer(val id: Int): GameScreenEvent()
    object OnMainButtonPressed:GameScreenEvent()
}
