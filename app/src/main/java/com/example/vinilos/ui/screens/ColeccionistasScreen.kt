package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.models.Response
import com.example.vinilos.ui.components.SearchField
import com.example.vinilos.ui.components.VinilosAppBar
import com.example.vinilos.ui.viewmodels.CollectorsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColeccionistasScreen(
    innerPadding: PaddingValues,
    viewModel: CollectorsViewModel,
    modifier: Modifier = Modifier,
) {
    val collectorsUiState = viewModel.uiState.collectAsState().value
    val collectorsResponse = collectorsUiState.collectorsResponse

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = innerPadding.calculateBottomPadding()),
    ) {
        VinilosAppBar(
            title = "Coleccionistas",
        )

        when (collectorsResponse) {
            is Response.Success -> {
                val collectors = viewModel.filterCollectors(
                    collectorsResponse.data
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SearchField(
                        value = collectorsUiState.collectorSearchTerm,
                        onChange = {
                            viewModel.setCollectorSearchTerm(it)
                        },
                        placeholderText = "Buscar coleccionistas",
                        onClear = { viewModel.setCollectorSearchTerm("") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (collectors.isEmpty())
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        ) {
                            Text("No se encontraron coleccionistas")
                        }
                    else
                        CollectorList(
                            collectors,
                            modifier = Modifier
                                .weight(1f),
                        )
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
            is Response.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(text = "Error al consultar los coleccionistas")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.getCollectors() }
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

@Composable
fun CollectorList(
    collectors: List<Collector>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        modifier = modifier,
    ) {
        items(collectors) { collector ->
            CollectorTile(collector)
        }
    }
}

@Composable
fun CollectorTile(
    collector: Collector,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = {},
        modifier = modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Spacer(
                modifier = Modifier.width(16.dp),
            )
            Text(
                collector.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
