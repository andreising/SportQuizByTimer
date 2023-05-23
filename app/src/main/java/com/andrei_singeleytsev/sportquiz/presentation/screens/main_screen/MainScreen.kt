package com.andrei_singeleytsev.sportquiz.presentation.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andrei_singeleytsev.sportquiz.R
import com.andrei_singeleytsev.sportquiz.data.room.entities.UserScore
import com.andrei_singeleytsev.sportquiz.presentation.utils.UIEvent


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val score = viewModel.score.value.collectAsState(initial = UserScore(null, 0))
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it) {
                is UIEvent.Navigate -> navController.navigate(it.route)
                else -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 40.dp),
                text = "Your Score",
                style = TextStyle(
                    fontSize = 40.sp
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Text(
                        text = score.value.score.toString(),
                        style = TextStyle(
                            fontSize = 80.sp
                        )
                    )
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(top = 30.dp),
                    painter = painterResource(id = R.drawable.coin),
                    contentDescription = "coin"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 10.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Choose Difficulty",
                    style = TextStyle(
                        fontSize = 30.sp
                    )
                )
                Button(onClick = {
                    viewModel.onEvent(MainScreenEvent.ToGameScreen(2))
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Easy")
                }
                Button(onClick = {
                    viewModel.onEvent(MainScreenEvent.ToGameScreen(5))
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Normal")
                }
                Button(
                    onClick = {
                        viewModel.onEvent(MainScreenEvent.ToGameScreen(10))
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Hard")
                }
                Text(text = "OR", modifier = Modifier.padding(top = 10.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(MainScreenEvent.ToWallpaperScreen)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green
                    )
                ) {
                    Text(text = "Go to shop!", color = Color.Black)
                }
            }
        }


    }
}