package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vinilos.data.models.Response
import com.example.vinilos.ui.state.AlbumesUiState

@Composable
fun AlbumesScreen(
    innerPadding: PaddingValues,
    albumesUiState: AlbumesUiState,
    modifier: Modifier = Modifier,
) {
    val albumesResponse = albumesUiState.albumesResponse

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        when (albumesResponse) {
            is Response.Success -> {
                val albums = albumesResponse.data
                Text(text = "Fetched ${albums.size} albums")
            }
            is Response.Error -> {
                val error = albumesResponse.msg
                Text(text = "Error: $error")
            }
            is Response.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}
