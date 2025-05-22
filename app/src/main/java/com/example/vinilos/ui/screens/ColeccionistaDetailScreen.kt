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
import com.example.vinilos.ui.components.VinilosAppBar
import com.example.vinilos.ui.state.CollectorDetailUiState
import com.example.vinilos.ui.viewmodels.CollectorDetailViewModel

@Composable
fun ColeccionistaDetailScreen(
    navController: NavController,
    collectorUiState: CollectorDetailUiState,
    viewModel: CollectorDetailViewModel,
    modifier: Modifier = Modifier,
    collectorId: Int
) {
    val collectorResponse = collectorUiState.collectorDetailResponse

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VinilosAppBar(
                title = "Detalle de coleccionista",
                onGoBack = { navController.navigateUp() }
            )
        },
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
            when (collectorResponse) {
                is Response.Success -> {
                    val collector = collectorResponse.data
                    val formattedPhone = "Teléfono: " + collector.telephone
                    val formattedEmail = "Correo: " + collector.email

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp)
                            .testTag("CollectorDetailContent"),
                    ) {
                        Text(
                            text = collector.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(top = 12.dp)
                        )
                        Text(
                            text = formattedPhone,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(top = 12.dp)
                        )
                        Text(
                            text = formattedEmail,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(top = 12.dp)
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
                        Text(text = "Error al consultar el coleccionista")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.getCollectorDetail(collectorId) }
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
