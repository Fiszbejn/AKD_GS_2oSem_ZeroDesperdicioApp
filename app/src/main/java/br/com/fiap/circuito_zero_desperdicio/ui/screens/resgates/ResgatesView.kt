package br.com.fiap.circuito_zero_desperdicio.ui.screens.resgates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.circuito_zero_desperdicio.data.local.PrefsHelper
import br.com.fiap.circuito_zero_desperdicio.data.model.OfertaState
import br.com.fiap.circuito_zero_desperdicio.ui.screens.home.HomeViewModel
import br.com.fiap.circuito_zero_desperdicio.ui.screens.home.OfertaCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResgatesView(
    onNavigateBack: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val prefs = remember { PrefsHelper(context) }
    val meusResgatesIds = prefs.getReservas()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Resgates") },
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
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            when (uiState) {
                is OfertaState.Loading -> CircularProgressIndicator()
                is OfertaState.Error -> Text("Erro ao carregar resgates.")
                is OfertaState.Success -> {
                    val todasOfertas = (uiState as OfertaState.Success).ofertas
                    val minhasOfertas = todasOfertas.filter { meusResgatesIds.contains(it.id) }

                    if (minhasOfertas.isEmpty()) {
                        Text("Você ainda não tem resgates ativos.")
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(minhasOfertas) { oferta ->
                                OfertaCard(
                                    oferta = oferta,
                                    isResgatado = true,
                                    onClick = { onNavigateToDetails(oferta.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}