package br.com.fiap.circuito_zero_desperdicio.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.circuito_zero_desperdicio.data.local.PrefsHelper
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(
    ofertaId: String,
    onNavigateBack: () -> Unit,
    viewModel: DetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val prefs = PrefsHelper(context)

    LaunchedEffect(ofertaId) {
        viewModel.carregarOferta(ofertaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Oferta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is DetalheState.Loading -> CircularProgressIndicator()
                is DetalheState.Error -> Text((uiState as DetalheState.Error).message)
                is DetalheState.Success -> {
                    val oferta = (uiState as DetalheState.Success).oferta

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AsyncImage(
                            model = oferta.imagemUrl,
                            contentDescription = "Imagem de ${oferta.titulo}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = oferta.titulo, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = oferta.estabelecimento, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(text = "Descrição", fontWeight = FontWeight.Bold)
                            Text(text = oferta.descricao)

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Distância: ${oferta.distancia}")
                            Text(text = "Status: ${oferta.status}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = {
                                    prefs.salvarReserva(oferta.id)
                                    Toast.makeText(context, "Reserva confirmada!", Toast.LENGTH_SHORT).show()
                                    onNavigateBack() // Volta para a tela anterior após reservar
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                            ) {
                                Text("Reservar para Retirada")
                            }
                        }
                    }
                }
            }
        }
    }
}