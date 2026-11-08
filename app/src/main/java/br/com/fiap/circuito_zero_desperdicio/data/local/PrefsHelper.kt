package br.com.fiap.circuito_zero_desperdicio.data.local

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ZeroDesperdicioPrefs", Context.MODE_PRIVATE)

    fun hasSeenOnboarding(): Boolean {
        return prefs.getBoolean("HAS_SEEN_INTRO", false)
    }

    fun setOnboardingSeen() {
        prefs.edit().putBoolean("HAS_SEEN_INTRO", true).apply()
    }

    // --- Funções para Meus Resgates ---
    fun salvarReserva(idOferta: String) {
        val reservasAtuais = getReservas().toMutableSet()
        reservasAtuais.add(idOferta)
        prefs.edit().putStringSet("MINHAS_RESERVAS", reservasAtuais).apply()
    }

    fun getReservas(): Set<String> {
        return prefs.getStringSet("MINHAS_RESERVAS", emptySet()) ?: emptySet()
    }

    // --- Funções para o Perfil ---
    fun salvarNomeUsuario(nome: String) {
        prefs.edit().putString("NOME_USUARIO", nome).apply()
    }

    fun getNomeUsuario(): String {
        return prefs.getString("NOME_USUARIO", "ONG Esperança") ?: "ONG Esperança"
    }

    fun limparDados() {
        prefs.edit().clear().apply() // Útil para o botão de "Sair" do app
    }
}