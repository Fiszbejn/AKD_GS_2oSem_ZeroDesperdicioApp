package br.com.fiap.circuito_zero_desperdicio.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.circuito_zero_desperdicio.data.model.Oferta
import br.com.fiap.circuito_zero_desperdicio.data.repository.OfertaRepository
import br.com.fiap.circuito_zero_desperdicio.data.repository.OfertaRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetalheState {
    object Loading : DetalheState()
    data class Success(val oferta: Oferta) : DetalheState()
    data class Error(val message: String) : DetalheState()
}

class DetailsViewModel(
    private val repository: OfertaRepository = OfertaRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetalheState>(DetalheState.Loading)
    val uiState: StateFlow<DetalheState> = _uiState

    fun carregarOferta(id: String) {
        viewModelScope.launch {
            _uiState.value = DetalheState.Loading
            try {
                val oferta = repository.getOferta(id)
                _uiState.value = DetalheState.Success(oferta)
            } catch (e: Exception) {
                _uiState.value = DetalheState.Error("Erro ao carregar detalhes do item.")
            }
        }
    }
}