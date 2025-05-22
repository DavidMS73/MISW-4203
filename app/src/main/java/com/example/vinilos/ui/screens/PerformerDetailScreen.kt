package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinilos.data.models.Response
import com.example.vinilos.ui.components.ImageCard
import com.example.vinilos.ui.components.VinilosAppBar
import com.example.vinilos.ui.state.PerformerDetailUiState
import com.example.vinilos.ui.viewmodels.PerformerDetailViewModel

@Composable
fun PerformerDetailScreen(
    navController: NavController,
    performerUiState: PerformerDetailUiState,
    viewModel: PerformerDetailViewModel,
    modifier: Modifier = Modifier,
    performerId: Int,
    onAlbumTap: (Int) -> Unit,
) {
    val performerResponse = performerUiState.performerDetailResponse

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VinilosAppBar(
                title = "Detalle de artista",
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
            when (performerResponse) {
                is Response.Success -> {
                    val performer = performerResponse.data

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("PerformerDetailSuccessScreen")
                    ) {
                        ImageCard(
                            imageUrl = performer.image,
                            title = performer.name,
                            modifier = Modifier
                                .padding(top = 12.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Albums",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(top = 12.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        if (performer.albums.isEmpty())
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                            ) {
                                Text("No se encontraron coleccionistas")
                            }
                        else
                            AlbumesList(
                                albums = performer.albums,
                                onAlbumTap = { id ->
                                    onAlbumTap(id)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("PerformerAlbumsList")
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
                        Text(text = "Error al consultar el artista")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.getPerformerDetail(performerId) }
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