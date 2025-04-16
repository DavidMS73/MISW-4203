package com.example.vinilos.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vinilos.R
import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.models.Response
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

    val performersResponse = performersUiState.performersResponse

    Column(
        modifier = modifier
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text(
                text = "Artistas",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
            )
        }, actions = {
            IconButton(
                onClick = {}, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ), modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Filled.Add, contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        })

        when (performersResponse) {
            is Response.Success -> {
                val artistas = viewModel.filterPerformers(
                    performersResponse.data
                )
                val focusRequester = remember { FocusRequester() }
                val focusManager = LocalFocusManager.current

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .testTag("PerformersSuccessScreen")
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        leadingIcon = {
                        Icon(
                            Icons.Filled.Search, contentDescription = null,
                        )
                    },
                        trailingIcon = {
                            if (performersUiState.performerSearchTerm.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        viewModel.setPerformerSearchTerm("")
                                        focusManager.clearFocus()
                                    }, modifier = Modifier.testTag("ClearButton")
                                ) {
                                    Icon(
                                        Icons.Filled.Cancel, contentDescription = null,
                                    )
                                }
                            }
                        },
                        value = performersUiState.performerSearchTerm,
                        placeholder = {
                            Text(text = "Buscar artistas")
                        },
                        onValueChange = {
                            Log.d("ArtistasScreen", "onValueChange: $it")
                            viewModel.setPerformerSearchTerm(it)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() },
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .testTag("PerformersSearchTextField")
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
                    Text(text = "Error al consultar los álbumes")
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
        modifier = modifier,
    ) {
        items(performers) {
            PerformerCard(it)
        }
    }
}

@Composable
fun PerformerCard(
    performer: Performer,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.testTag("PerformerCard-${performer.id}"),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(performer.image)
                .crossfade(true).build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.loading_img),
            error = painterResource(R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = performer.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}