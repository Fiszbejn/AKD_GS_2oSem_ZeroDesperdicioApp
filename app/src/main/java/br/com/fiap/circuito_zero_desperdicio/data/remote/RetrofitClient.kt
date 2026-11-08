package br.com.fiap.circuito_zero_desperdicio.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://6aefef92b9925b27ce17123f.mockapi.io/ZeroDesperdicioApp/api/"

    val apiService: OfertaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OfertaService::class.java)
    }
}