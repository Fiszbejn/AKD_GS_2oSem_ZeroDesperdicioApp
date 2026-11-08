package br.com.fiap.circuito_zero_desperdicio.data.model

sealed class OfertaState {
    object Loading : OfertaState()
    data class Success(val ofertas: List<Oferta>) : OfertaState()
    data class Error(val message: String) : OfertaState()
}