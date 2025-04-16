package com.example.vinilos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinilos.ui.navigation.Home
import com.example.vinilos.ui.screens.VinilosHome
import com.example.vinilos.ui.viewmodels.AlbumesViewModel
import com.example.vinilos.ui.viewmodels.PerformersViewModel

@Composable
fun VinilosApp(
    navController: NavHostController = rememberNavController(),
    albumesViewModel: AlbumesViewModel = viewModel(factory = AlbumesViewModel.Factory),
    performersViewModel: PerformersViewModel = viewModel(factory = PerformersViewModel.Factory)
) {
    val albumesUiState = albumesViewModel.uiState.collectAsState().value
    val performersUiState = performersViewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Home> {
            VinilosHome(
                navController = navController,
                albumesViewModel = albumesViewModel,
                albumesUiState = albumesUiState,
                performersViewModel = performersViewModel,
                performersUiState = performersUiState
            )
        }
    }
}
