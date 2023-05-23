package com.andrei_singeleytsev.sportquiz.domain.repository

import androidx.compose.runtime.MutableState
import com.andrei_singeleytsev.sportquiz.domain.models.QuestionItem


interface GameHelperProvider {
    suspend fun getQuestion(): QuestionItem
    fun getDifficulty():Int
    fun prepareGameSettings(difficulty:Int)
}