package com.andrei_singeleytsev.sportquiz.presentation.screens.wallpapers_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andrei_singeleytsev.sportquiz.R
import com.andrei_singeleytsev.sportquiz.data.room.entities.PictureModel
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import com.andrei_singeleytsev.sportquiz.presentation.utils.UIEvent
import com.andrei_singeleytsev.sportquizapp.presentation.dialog.DialogController
import com.andrei_singeleytsev.sportquizapp.presentation.dialog.DialogEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WallpapersScreen(
    viewModel: WallpaperScreenViewModel = hiltViewModel()
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    viewModel.activityContext = LocalContext.current as? Activity
    val score = viewModel.score.collectAsState(initial = UserScore(null, 0))
    val listState = viewModel.pictures.collectAsState(initial = emptyList<PictureModel>())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        it.message
                    )
                }

                else -> {}
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Total coins: " + score.value.score.toString(),
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                )
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp),
                    painter = painterResource(id = R.drawable.coin),
                    contentDescription = "coin"
                )
            }


            val pagerState = rememberPagerState()
            Carousel(list = listState.value as List<PictureModel>, pagerState = pagerState)
        }
    }
    NoteDialog(dialogController = viewModel)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel(list: List<PictureModel>, pagerState: PagerState, viewModel: WallpaperScreenViewModel = hiltViewModel()){
    HorizontalPager(
        count = list.size,
        state = pagerState,
        modifier = Modifier
            .padding(10.dp)
    ) {
        val pictureModel = list[it]
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = pictureModel.pictureId),
                contentDescription = null,
                modifier = Modifier.size(width = 250.dp, height = 500.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = {
                    viewModel.onEvent(WallpapersScreenEvent.MainButtonPressed(pictureModel))
                }, modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                if (pictureModel.isEnabled) Text(text = "Set as wallpaper")
                else Text(text = "Buy picture")
            }
        }


    }
}


@Composable
fun NoteDialog(
    dialogController: DialogController, viewModel: WallpaperScreenViewModel = hiltViewModel()
) {
    if (dialogController.openDialog.value) {
        AlertDialog( containerColor = Color.White, onDismissRequest = {
            dialogController.onDialogEvent(DialogEvent.OnCancel)
        },
            title = null,
            text = {
                Text( text = dialogController.dialogTitle.value,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnConfirm)
                }) {
                    Text(text = "Buy")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnCancel)
                }, enabled = viewModel.isEnabled.value) {
                    Text(text = "Cancel")
                }
            })
    }
}