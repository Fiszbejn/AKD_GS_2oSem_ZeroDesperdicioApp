package br.com.fiap.circuito_zero_desperdicio.data.repository

import br.com.fiap.circuito_zero_desperdicio.data.model.Oferta
import br.com.fiap.circuito_zero_desperdicio.data.remote.RetrofitClient

interface OfertaRepository {
    suspend fun getOfertas(): List<Oferta>
    suspend fun getOferta(id: String): Oferta
}

class OfertaRepositoryImpl : OfertaRepository {
    override suspend fun getOfertas(): List<Oferta> {
        return RetrofitClient.apiService.getOfertas()
    }

    override suspend fun getOferta(id: String): Oferta {
        return RetrofitClient.apiService.getOferta(id)
    }
}