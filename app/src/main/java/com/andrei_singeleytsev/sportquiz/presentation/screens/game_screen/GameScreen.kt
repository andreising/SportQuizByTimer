package com.andrei_singeleytsev.sportquiz.presentation.screens.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andrei_singeleytsev.sportquiz.R
import com.andrei_singeleytsev.sportquiz.presentation.utils.UIEvent

@Composable
fun GameScreen(viewModel: GameScreenViewModel = hiltViewModel(), onBack: ()->Unit){

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it) {
                is UIEvent.PopBackStack -> onBack()
                else -> {}
            }
        }
    }
    val difficulty = when(viewModel.difficulty){
        1-> "Easy"
        2-> "Normal"
        3-> "Hard"
        else -> ""
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Difficulty: $difficulty",
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    ),
                    textAlign = TextAlign.Center
                )
                if (viewModel.gameState.value == GameScreenViewModel.GameState.GAME_START) Text(
                    text = "You have to answer the questions in one minute",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                )
                else GameContent()
                Button(
                    onClick = {
                        viewModel.onEvent(GameScreenEvent.OnMainButtonPressed)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                        enabled = viewModel.gameState.value!=GameScreenViewModel.GameState.GAME_IS_ON
                ) {
                    val text = when(viewModel.gameState.value) {
                        GameScreenViewModel.GameState.GAME_START -> "Start game!"
                        GameScreenViewModel.GameState.GAME_FINISHED -> "To Main Screen!"
                        else -> "Next Question!"
                    }
                    Text(text = text)
                }
            }
        }

    }

@Composable
fun GameContent(viewModel: GameScreenViewModel = hiltViewModel()){
    Text(
        text = viewModel.timeAsString.value,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        style = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp
        ),
        textAlign = TextAlign.Center
    )
    Column(modifier = Modifier.background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = viewModel.currentScore.value.toString(),
            style = TextStyle(
                fontSize = 80.sp
            )
        )
        Image(
            modifier = Modifier
                .size(70.dp),
            painter = painterResource(id = R.drawable.coin),
            contentDescription = "coin"
        )
    }

    Text(
        text = "Question:",
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        style = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        textAlign = TextAlign.Center
    )
    Text(
        text = viewModel.question.value,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        style = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        textAlign = TextAlign.Center
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                TextButton(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (viewModel.gameState.value == GameScreenViewModel.GameState.GAME_IS_ON) viewModel.onEvent(
                            GameScreenEvent.SendAnswer(0)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.colors[0].value
                    )
                ) {
                    Text(
                        text = viewModel.answer0.value,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        if (viewModel.gameState.value == GameScreenViewModel.GameState.GAME_IS_ON) viewModel.onEvent(
                            GameScreenEvent.SendAnswer(1)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.colors[1].value
                    )
                ) {
                    Text(
                        text = viewModel.answer1.value,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (viewModel.gameState.value == GameScreenViewModel.GameState.GAME_IS_ON) viewModel.onEvent(
                            GameScreenEvent.SendAnswer(2)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.colors[2].value
                    )
                ) {
                    Text(
                        text = viewModel.answer2.value,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        if (viewModel.gameState.value == GameScreenViewModel.GameState.GAME_IS_ON) viewModel.onEvent(
                            GameScreenEvent.SendAnswer(3))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.colors[3].value
                    )
                ) {
                    Text(
                        text = viewModel.answer3.value,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
