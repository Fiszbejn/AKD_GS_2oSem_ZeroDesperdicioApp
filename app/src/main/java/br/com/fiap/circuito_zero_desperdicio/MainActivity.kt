package br.com.fiap.circuito_zero_desperdicio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.circuito_zero_desperdicio.ui.navigation.AppNavigation
import br.com.fiap.circuito_zero_desperdicio.ui.theme.Circuito_zero_desperdicioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Circuito_zero_desperdicioTheme {
                AppNavigation()
            }
        }
    }
}