package com.example.vinilos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.models.Response
import com.example.vinilos.ui.state.AlbumesUiState
import com.example.vinilos.ui.viewmodels.AlbumesViewModel
import com.example.vinilos.ui.components.ImageCard
import com.example.vinilos.ui.components.SearchField
import com.example.vinilos.ui.components.VinilosAppBar
import com.example.vinilos.ui.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumesScreen(
    albumesUiState: AlbumesUiState,
    viewModel: AlbumesViewModel,
    modifier: Modifier = Modifier,
    onAlbumTap: (Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val albumesResponse = albumesUiState.albumesResponse

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = innerPadding.calculateBottomPadding())
    ) {
        VinilosAppBar(
            title = "Álbumes",
            onAddTap = {},
        )

        when (albumesResponse) {
            is Response.Success -> {
                val albums = viewModel.filterAlbums(
                    albumesResponse.data
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("AlbumesSuccessScreen")
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SearchField(
                        value = albumesUiState.albumSearchTerm,
                        onChange = {
                            viewModel.setAlbumSearchTerm(it)
                        },
                        placeholderText = "Buscar álbumes",
                        onClear = { viewModel.setAlbumSearchTerm("") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (albums.isEmpty())
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        ) {
                            Text("No se encontraron álbumes")
                        }
                    else
                        AlbumesList(
                            albums = albums,
                            onAlbumTap = { id ->
                                onAlbumTap(id)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("AlbumesList")
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
                    Text(text = "Error al consultar los álbumes")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.getAlbumes() }
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

@Composable
fun AlbumesList(
    albums: List<Album>,
    modifier: Modifier = Modifier,
    onAlbumTap: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        modifier = modifier,
    ) {
        items(albums) {
            ImageCard(
                imageUrl = it.cover,
                title = it.name,
                modifier = Modifier
                    .clickable(onClick = { onAlbumTap(it.id) })
                    .testTag("AlbumCard-${it.id}")
            )
        }
    }
}
