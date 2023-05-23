package com.andrei_singeleytsev.sportquiz.presentation.screens.wallpapers_screen

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrei_singeleytsev.sportquiz.R
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import com.andrei_singeleytsev.sportquiz.data.room.repository.PictureModelRepository
import com.andrei_singeleytsev.sportquiz.data.room.repository.UserScoreRepository
import com.andrei_singeleytsev.sportquiz.presentation.utils.UIEvent
import com.andrei_singeleytsev.sportquizapp.presentation.dialog.DialogController
import com.andrei_singeleytsev.sportquizapp.presentation.dialog.DialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WallpaperScreenViewModel @Inject constructor(
    private val userScoreRepository: UserScoreRepository,
    private val pictureModelRepository: PictureModelRepository
): ViewModel(), DialogController {
    val score = userScoreRepository.getScore(1)
    @SuppressLint("StaticFieldLeak")
    var activityContext: Context? = null
    val pictures = pictureModelRepository.getPictures()
    val isEnabled = mutableStateOf(true)
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var currentScore: Int? = null
    private var currentPicture: PictureModel? = null
    fun onEvent(event: WallpapersScreenEvent){
        when(event) {
            is WallpapersScreenEvent.MainButtonPressed -> {
                event.pictureModel.apply {
                    currentPicture = this@apply
                    if (isEnabled) {
                        setImageAsWallpaper()
                    } else {
                        Log.d("tag", "123")
                        viewModelScope.launch {
                            currentScore = score.first().score
                            if (checkPayments(currentScore!!, currentPicture!!.coast)) {
                                openDialog.value = true
                            } else {
                                sendUIEvent(UIEvent.ShowSnackBar("Not enough coins"))
                            }
                        }
                    }

                    }
                }
            }
        }

    private fun setImageAsWallpaper() {
        isEnabled.value = false
        val wallpaperManager = WallpaperManager.getInstance(activityContext)
        val bitmap = BitmapFactory.decodeResource(activityContext?.resources, currentPicture!!.pictureId)

        try {
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(activityContext, "Image has set", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(activityContext, "Error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        isEnabled.value = true
    }


    private fun sendUIEvent(event: UIEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    override var dialogTitle =  mutableStateOf("Do you want to unlock this picture?")
        private set
    override var openDialog = mutableStateOf(false)
        private set

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            DialogEvent.OnCancel ->{
                openDialog.value = false
                currentScore = null
                currentPicture = null
            }
            DialogEvent.OnConfirm->{
                viewModelScope.launch {
                    pictureModelRepository.unlockPicture(currentPicture!!.copy(isEnabled = true))
                    userScoreRepository.insertItem(UserScore(1, currentScore!! - currentPicture!!.coast))
                    currentScore = null
                    currentPicture = null
                    openDialog.value = false
                }

            }
        }
    }
    private fun checkPayments(score: Int, coast: Int): Boolean {
        return coast<=score
    }
}