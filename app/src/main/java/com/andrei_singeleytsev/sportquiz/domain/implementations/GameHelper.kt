package com.andrei_singeleytsev.sportquiz.domain.implementations

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.andrei_singeleytsev.sportquiz.domain.models.QuestionItem
import com.andrei_singeleytsev.sportquiz.domain.repository.GameHelperProvider
import com.andrei_singeleytsev.sportquizapp.domain.data.ListOfQuestions

class GameHelper: GameHelperProvider {
    private var gameDifficulty = 1

    private var list = mutableListOf<QuestionItem>()

    override suspend fun getQuestion(): QuestionItem {
        if (list.isEmpty()) list = ListOfQuestions.Hard.array.toMutableList()
        val question = list.random()
        list.remove(question)
        return question
    }

    override fun getDifficulty(): Int{
        return gameDifficulty
    }
    override fun prepareGameSettings(difficulty: Int){
        gameDifficulty = difficulty
        list = when(difficulty) {
            2-> ListOfQuestions.Easy.array.toMutableList()
            5->ListOfQuestions.Medium.array.toMutableList()
            10->ListOfQuestions.Hard.array.toMutableList()
            else -> {
                mutableListOf()
            }
        }
    }
}