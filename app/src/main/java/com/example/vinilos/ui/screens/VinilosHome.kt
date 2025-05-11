package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.vinilos.ui.models.BottomNavRoute
import com.example.vinilos.ui.state.AlbumesUiState
import com.example.vinilos.ui.state.PerformersUiState
import com.example.vinilos.ui.viewmodels.AlbumesViewModel
import com.example.vinilos.ui.viewmodels.CollectorsViewModel
import com.example.vinilos.ui.viewmodels.PerformersViewModel

val routes = listOf(
    BottomNavRoute(
        icon = Icons.AutoMirrored.Filled.MenuBook,
        name = "Álbumes",
    ),
    BottomNavRoute(
        icon = Icons.Filled.Person3,
        name = "Coleccionistas",
    ),
    BottomNavRoute(
        icon = Icons.Filled.Headset,
        name = "Artistas",
    ),
)

@Composable
fun VinilosHome(
    navController: NavController,
    albumesViewModel: AlbumesViewModel,
    albumesUiState: AlbumesUiState,
    performersViewModel: PerformersViewModel,
    performersUiState: PerformersUiState,
    collectorsViewModel: CollectorsViewModel,
) {
    var selectedIdx by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                routes = routes,
                onItemTap = { selectedIdx = it },
                selectedIdx = selectedIdx,
            )
        }
    ) { innerPadding ->
        when (selectedIdx) {
            0 -> AlbumesScreen(
                innerPadding = innerPadding,
                albumesUiState = albumesUiState,
                viewModel = albumesViewModel,
                navController = navController,
            )
            1 -> ColeccionistasScreen(
                innerPadding = innerPadding,
                viewModel = collectorsViewModel,
            )
            2 -> ArtistasScreen(
                innerPadding = innerPadding,
                performersUiState = performersUiState,
                viewModel = performersViewModel
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    routes: List<BottomNavRoute>,
    onItemTap: (Int) -> Unit,
    selectedIdx: Int,
) {
    NavigationBar {
        for ((idx, route) in routes.withIndex()) {
            NavigationBarItem(
                icon = {
                    Icon(
                        route.icon,
                        contentDescription = route.name,
                    )
                },
                label = { Text(route.name) },
                selected = selectedIdx == idx,
                onClick = {
                    onItemTap(idx)
                }
            )
        }
    }
}
