package com.example.vinilos.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.models.Response
import com.example.vinilos.ui.components.ImageCard
import com.example.vinilos.ui.components.SearchField
import com.example.vinilos.ui.components.VinilosAppBar
import com.example.vinilos.ui.state.PerformersUiState
import com.example.vinilos.ui.viewmodels.PerformersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistasScreen(
    performersUiState: PerformersUiState,
    viewModel: PerformersViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val performersResponse = performersUiState.performersResponse

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = innerPadding.calculateBottomPadding())
    ) {
        VinilosAppBar(
            title = "Artistas",
            onAddTap = {},
        )

        when (performersResponse) {
            is Response.Success -> {
                val artistas = viewModel.filterPerformers(
                    performersResponse.data
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("PerformersSuccessScreen")
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SearchField(
                        value = performersUiState.performerSearchTerm,
                        onChange = {
                            viewModel.setPerformerSearchTerm(it)
                        },
                        placeholderText = "Buscar artistas",
                        onClear = { viewModel.setPerformerSearchTerm("") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (artistas.isEmpty()) Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        Text("No se encontraron artistas")
                    }
                    else PerformerList(
                        performers = artistas, modifier = Modifier
                            .weight(1f)
                            .testTag("PerformerList")
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
                    Text(text = "Error al consultar los artistas")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.getPerformers() }) {
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
fun PerformerList(
    performers: List<Performer>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        modifier = modifier,
    ) {
        items(performers) {
            ImageCard(
                imageUrl = it.image,
                title = it.name,
                modifier = Modifier
                    .testTag("PerformerCard-${it.id}")
            )
        }
    }
}
