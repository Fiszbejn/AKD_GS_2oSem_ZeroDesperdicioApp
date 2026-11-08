package br.com.fiap.circuito_zero_desperdicio.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.circuito_zero_desperdicio.data.local.PrefsHelper
import kotlinx.coroutines.delay

@Composable
fun SplashView(onNavigateToHome: () -> Unit, onNavigateToIntro: () -> Unit) {
    val context = LocalContext.current
    val prefs = PrefsHelper(context)

    LaunchedEffect(Unit) {
        delay(2000)
        if (prefs.hasSeenOnboarding()) {
            onNavigateToHome()
        } else {
            onNavigateToIntro()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = "Logo Zero Desperdício",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Zero Desperdício",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}