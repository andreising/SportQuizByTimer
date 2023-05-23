package com.andrei_singeleytsev.sportquiz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andrei_singeleytsev.sportquiz.presentation.screens.game_screen.GameScreen
import com.andrei_singeleytsev.sportquiz.presentation.screens.main_screen.MainScreen
import com.andrei_singeleytsev.sportquiz.presentation.screens.wallpapers_screen.WallpapersScreen
import com.andrei_singeleytsev.sportquiz.presentation.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)
        }
        composable(Routes.GAME_SCREEN) {
            GameScreen(){
                navController.popBackStack()
            }
        }
        composable(Routes.WALLPAPERS_SCREEN) {
            WallpapersScreen()
        }
    }
}