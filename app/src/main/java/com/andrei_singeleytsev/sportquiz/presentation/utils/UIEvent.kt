package com.andrei_singeleytsev.sportquiz.presentation.utils

sealed class UIEvent{
    object PopBackStack: UIEvent()
    data class Navigate(val route: String): UIEvent()
    data class ShowSnackBar(val message: String): UIEvent()
}