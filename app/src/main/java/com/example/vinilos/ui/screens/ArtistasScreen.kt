package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vinilos.ui.state.PerformersUiState
import com.example.vinilos.ui.viewmodels.PerformersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistasScreen(
    performersUiState: PerformersUiState,
    viewModel: PerformersViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Text("Artistas Screen")
    }
}