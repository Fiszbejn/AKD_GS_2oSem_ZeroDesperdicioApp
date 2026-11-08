package br.com.fiap.circuito_zero_desperdicio.data.model

import com.google.gson.annotations.SerializedName

data class Oferta(
    val id: String,
    val titulo: String,
    val estabelecimento: String,
    val descricao: String,
    val distancia: String,
    val status: String,
    @SerializedName("imagemUrl") val imagemUrl: String
)