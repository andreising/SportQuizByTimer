package com.andrei_singeleytsev.sportquiz.presentation.screens.main_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrei_singeleytsev.sportquiz.R
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import com.andrei_singeleytsev.sportquiz.data.room.repository.PictureModelRepository
import com.andrei_singeleytsev.sportquiz.data.room.repository.UserScoreRepository
import com.andrei_singeleytsev.sportquiz.domain.repository.GameHelperProvider
import com.andrei_singeleytsev.sportquiz.presentation.utils.Routes
import com.andrei_singeleytsev.sportquiz.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
private val userScoreRepository: UserScoreRepository,
private val pictureModelRepository: PictureModelRepository,
private val gameHelperProvider: GameHelperProvider
): ViewModel() {

    init {
        viewModelScope.launch {
            getData()
        }
    }



    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var score = mutableStateOf(flowOf(UserScore(null, 0)))

    fun onEvent(event: MainScreenEvent){
        when(event) {
            is MainScreenEvent.ToGameScreen -> {
                setGameDifficulty(event.difficulty)
                sendUIEvent(UIEvent.Navigate(Routes.GAME_SCREEN))
            }
            is MainScreenEvent.ToWallpaperScreen -> {
                sendUIEvent(UIEvent.Navigate(Routes.WALLPAPERS_SCREEN))
            }
        }

    }

    private fun setGameDifficulty(difficulty: Int) {
        viewModelScope.launch {
            gameHelperProvider.prepareGameSettings(difficulty)
        }
    }

    private fun sendUIEvent(event: UIEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private suspend fun getData() {
        try {
            score.value=userScoreRepository.getScore(1)
            score.value.collect{
                Log.d("tag", it.score.toString())
            }
        } catch (e: NullPointerException) {
            addPicturesToRoom()
            createNewScore()
            score.value=userScoreRepository.getScore(1)

        }

    }

    private suspend fun createNewScore() {
        userScoreRepository.insertItem(UserScore(null,5))
    }

    private suspend fun addPicturesToRoom() {
        START_LIST.forEach {
            pictureModelRepository.loadPicture(it)
        }
    }
}

val START_LIST = listOf(
    PictureModel(null, R.drawable.wall1, 5),
    PictureModel(null, R.drawable.wall2, 10),
    PictureModel(null, R.drawable.wall3, 10),
    PictureModel(null, R.drawable.wall4, 10),
    PictureModel(null, R.drawable.wall5, 15),
    PictureModel(null, R.drawable.wall6, 15),
    PictureModel(null, R.drawable.wall7, 15),
    PictureModel(null, R.drawable.wall8, 15),
    PictureModel(null, R.drawable.wall9, 15),
    PictureModel(null, R.drawable.wall10, 15),
)