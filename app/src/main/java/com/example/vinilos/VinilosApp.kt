package com.example.vinilos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vinilos.ui.navigation.NavigationItem
import com.example.vinilos.ui.screens.AlbumDetailScreen
import com.example.vinilos.ui.screens.ColeccionistaDetailScreen
import com.example.vinilos.ui.screens.VinilosHome
import com.example.vinilos.ui.viewmodels.AlbumDetailViewModel
import com.example.vinilos.ui.viewmodels.AlbumesViewModel
import com.example.vinilos.ui.viewmodels.CollectorDetailViewModel
import com.example.vinilos.ui.viewmodels.CollectorsViewModel
import com.example.vinilos.ui.viewmodels.PerformersViewModel

@Composable
fun VinilosApp(
    navController: NavHostController = rememberNavController(),
    albumesViewModel: AlbumesViewModel = viewModel(factory = AlbumesViewModel.Factory),
    performersViewModel: PerformersViewModel = viewModel(factory = PerformersViewModel.Factory),
    collectorsViewModel: CollectorsViewModel = viewModel(factory = CollectorsViewModel.Factory),
) {
    val albumesUiState = albumesViewModel.uiState.collectAsState().value
    val performersUiState = performersViewModel.uiState.collectAsState().value
    val collectorsUiState = collectorsViewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(NavigationItem.Home.route) {
            VinilosHome(
                navController = navController,
                albumesViewModel = albumesViewModel,
                albumesUiState = albumesUiState,
                performersViewModel = performersViewModel,
                performersUiState = performersUiState,
                collectorsViewModel = collectorsViewModel,
                collectorsUiState = collectorsUiState
            )
        }
        composable(
            NavigationItem.AlbumDetail.route + "/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->

            val albumId = backStackEntry.arguments?.getInt("albumId")!!

            // Get the application context
            val application = LocalContext.current.applicationContext as VinilosApplication
            val albumesRepository = application.container.albumesRepository

            // Create ViewModel using the provided factory with the albumId
            val albumDetailViewModel: AlbumDetailViewModel = viewModel(
                factory = AlbumDetailViewModel.provideFactory(albumesRepository, albumId)
            )

            val albumDetailUiState = albumDetailViewModel.uiState.collectAsState().value

            AlbumDetailScreen(
                navController = navController,
                viewModel = albumDetailViewModel,
                albumesUiState = albumDetailUiState,
                albumId = albumId
            )
        }
        composable(
            NavigationItem.CollectorDetail.route + "/{collectorId}",
            arguments = listOf(navArgument("collectorId") { type = NavType.IntType })
        ) { backStackEntry ->

            val collectorId = backStackEntry.arguments?.getInt("collectorId")!!

            // Get the application context
            val application = LocalContext.current.applicationContext as VinilosApplication
            val collectorRepository = application.container.collectorsRepository

            // Create ViewModel using the provided factory with the collectorId
            val collectorDetailViewModel: CollectorDetailViewModel = viewModel(
                factory = CollectorDetailViewModel.provideFactory(collectorRepository, collectorId)
            )

            val collectorDetailUiState = collectorDetailViewModel.uiState.collectAsState().value

            ColeccionistaDetailScreen(
                navController = navController,
                viewModel = collectorDetailViewModel,
                collectorUiState = collectorDetailUiState,
                collectorId = collectorId
            )
        }
    }
}
