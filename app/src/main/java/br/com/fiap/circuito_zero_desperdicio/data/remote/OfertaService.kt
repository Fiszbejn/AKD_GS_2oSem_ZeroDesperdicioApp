package br.com.fiap.circuito_zero_desperdicio.data.remote

import br.com.fiap.circuito_zero_desperdicio.data.model.Oferta
import retrofit2.http.GET
import retrofit2.http.Path

interface OfertaService {
    @GET("ofertas")
    suspend fun getOfertas(): List<Oferta>

    @GET("ofertas/{id}")
    suspend fun getOferta(@Path("id") id: String): Oferta
}