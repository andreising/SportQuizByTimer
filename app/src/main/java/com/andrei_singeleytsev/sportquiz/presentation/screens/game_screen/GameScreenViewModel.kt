package com.andrei_singeleytsev.sportquiz.presentation.screens.game_screen

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import com.andrei_singeleytsev.sportquiz.data.room.repository.UserScoreRepository
import com.andrei_singeleytsev.sportquiz.domain.models.QuestionItem
import com.andrei_singeleytsev.sportquiz.domain.repository.GameHelperProvider
import com.andrei_singeleytsev.sportquiz.presentation.theme.lightBlue
import com.andrei_singeleytsev.sportquiz.presentation.utils.TimeUtils
import com.andrei_singeleytsev.sportquiz.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val gameHelper: GameHelperProvider,
    private val userScoreRepository: UserScoreRepository
): ViewModel() {
    private var questionItem = QuestionItem("", emptyList())
    init {
        viewModelScope.launch {
            questionItem = gameHelper.getQuestion()
            Log.d("tag", questionItem.toString())
        }
    }
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val gameState = mutableStateOf(GameState.GAME_START)
    val currentScore = mutableStateOf(0)
    val difficulty = gameHelper.getDifficulty()
    val question = mutableStateOf("")
    val answer0 = mutableStateOf("")
    val answer1 = mutableStateOf("")
    val answer2 = mutableStateOf("")
    val answer3 = mutableStateOf("")
    val timeAsString = mutableStateOf("1:00")
    val colors = listOf(
        mutableStateOf(lightBlue),
        mutableStateOf(lightBlue),
        mutableStateOf(lightBlue),
        mutableStateOf(lightBlue)
    )
    private var shuffledList = mutableListOf<String>()
    private var currentButton = 0
    private var time = 60000L

    var timer = object : CountDownTimer(time, 1000){
        override fun onTick(p0: Long) {
            time = p0
            timeAsString.value =TimeUtils.getTime(p0)
        }

        override fun onFinish() {
            endGame()
        }
    }

    fun onEvent(event: GameScreenEvent){
        when(event) {
            is GameScreenEvent.OnMainButtonPressed -> {
                when(gameState.value){
                    GameState.GAME_START -> {
                        generateUIAnswer()
                        gameState.value = GameState.GAME_IS_ON
                        timer.start()
                    }
                    GameState.GAME_FINISHED -> {
                        sendUIEvent(UIEvent.PopBackStack)
                    }
                }
            }
            is GameScreenEvent.SendAnswer -> {
                if (isAnswerRight(event.id)) {
                    currentScore.value+=difficulty
                }
                generateUIAnswer()
            }

        }

    }

    private fun sendUIEvent(event: UIEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun insertScore(){
        viewModelScope.launch {
            val oldScore = userScoreRepository.getScore(1).first()
            val newScore = oldScore.score+currentScore.value
            userScoreRepository.insertItem(oldScore.copy(score = newScore))
        }
    }

    private fun isAnswerRight(buttonIndex: Int): Boolean {
        return buttonIndex == currentButton
    }

    private fun generateUIAnswer(){
        question.value = questionItem.question
        shuffledList = questionItem.answers.toMutableList()
        shuffledList.shuffle()
        answer0.value = shuffledList[0]
        answer1.value = shuffledList[1]
        answer2.value = shuffledList[2]
        answer3.value = shuffledList[3]
        currentButton = shuffledList.indexOf(questionItem.answers[0])
        viewModelScope.launch {
            questionItem = gameHelper.getQuestion()
        }
    }

    private fun endGame() {
        gameState.value = GameState.GAME_FINISHED
        insertScore()
        colors[currentButton].value = Color.Green
    }

    object GameState {
        const val GAME_START = "game_start"
        const val GAME_IS_ON = "game_is_on"
        const val GAME_FINISHED = "game_finished"
    }
}