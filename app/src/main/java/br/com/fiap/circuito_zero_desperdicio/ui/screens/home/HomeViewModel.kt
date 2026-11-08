package br.com.fiap.circuito_zero_desperdicio.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.circuito_zero_desperdicio.data.model.OfertaState
import br.com.fiap.circuito_zero_desperdicio.data.repository.OfertaRepository
import br.com.fiap.circuito_zero_desperdicio.data.repository.OfertaRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: OfertaRepository = OfertaRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow<OfertaState>(OfertaState.Loading)
    val uiState: StateFlow<OfertaState> = _uiState

    init {
        carregarOfertas()
    }

    fun carregarOfertas() {
        viewModelScope.launch {
            _uiState.value = OfertaState.Loading
            try {
                val ofertas = repository.getOfertas()
                _uiState.value = OfertaState.Success(ofertas)
            } catch (e: Exception) {
                _uiState.value = OfertaState.Error("Erro ao buscar dados. Verifique a internet.")
            }
        }
    }
}