package br.com.fiap.circuito_zero_desperdicio.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.circuito_zero_desperdicio.data.local.PrefsHelper
import br.com.fiap.circuito_zero_desperdicio.data.model.Oferta
import br.com.fiap.circuito_zero_desperdicio.data.model.OfertaState
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToDetails: (String) -> Unit,
    onNavigateToResgates: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val prefs = remember { PrefsHelper(context) }

    val meusResgatesIds = prefs.getReservas()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ofertas na Região", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToResgates) {
                        Icon(Icons.Default.List, contentDescription = "Meus Resgates", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(onClick = onNavigateToPerfil) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
                is OfertaState.Loading -> {
                    CircularProgressIndicator()
                }
                is OfertaState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = (uiState as OfertaState.Error).message)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.carregarOfertas() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Tentar novamente")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Tentar Novamente")
                        }
                    }
                }
                is OfertaState.Success -> {
                    val ofertas = (uiState as OfertaState.Success).ofertas
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(ofertas) { oferta ->
                            val isResgatado = meusResgatesIds.contains(oferta.id)

                            OfertaCard(
                                oferta = oferta,
                                isResgatado = isResgatado, // Passa o status para o Card
                                onClick = { onNavigateToDetails(oferta.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OfertaCard(oferta: Oferta, isResgatado: Boolean, onClick: () -> Unit) {
    // Define o texto que vai aparecer: Se o usuário resgatou, mostra "Resgatado", senão mostra o que veio da API
    val statusExibicao = if (isResgatado) "Resgatado" else oferta.status

    // Verifica se está indisponível por qualquer motivo
    val isIndisponivel = statusExibicao == "Indisponível" || statusExibicao == "Resgatado"

    // Define a cor: error (vermelho) para indisponível, primary (azul/verde) para disponível
    val corStatus = if (isIndisponivel) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isIndisponivel) { onClick() }, // Desativa o clique se estiver indisponível
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // Deixa o card levemente transparente se estiver indisponível
        colors = CardDefaults.cardColors(
            containerColor = if (isIndisponivel) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = oferta.imagemUrl,
                contentDescription = "Imagem de ${oferta.titulo}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = oferta.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = oferta.estabelecimento, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = oferta.distancia, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    // Aqui aplicamos a cor dinâmica e o peso da fonte[cite: 4]
                    Text(
                        text = statusExibicao,
                        color = corStatus,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}