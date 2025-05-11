package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vinilos.data.models.Response
import com.example.vinilos.ui.components.ImageCard
import com.example.vinilos.ui.components.VinilosAppBar
import com.example.vinilos.ui.state.AlbumDetailUiState
import com.example.vinilos.ui.viewmodels.AlbumDetailViewModel
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    navController: NavController,
    albumesUiState: AlbumDetailUiState,
    viewModel: AlbumDetailViewModel,
    modifier: Modifier = Modifier,
    albumId: Int
) {
    val albumResponse = albumesUiState.albumDetailResponse

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VinilosAppBar(
                title = "Detalle de álbum",
                onGoBack = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                )
        ) {
            when (albumResponse) {
                is Response.Success -> {
                    val album = albumResponse.data
                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    val formatter = SimpleDateFormat("dd-MM-yyyy")
                    val formattedDate = formatter.format(parser.parse(album.releaseDate))

                    ImageCard(
                        imageUrl = album.cover,
                        title = album.name,
                        modifier = Modifier
                            .testTag("AlbumDetailCard-${album.id}"),
                        imageHeight = 300,
                        imagePadding = 8,
                        textStyleTypography = MaterialTheme.typography.titleLarge,
                        textFontWeight = FontWeight.Bold
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        Text(
                            text = album.recordLabel,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier.padding(start = 15.dp, end = 15.dp, top = 30.dp),
                    ) {
                        Text(
                            text = album.genre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = album.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }

                is Response.Error -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Error al consultar el álbum")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.getAlbumDetail(albumId) }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                is Response.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
