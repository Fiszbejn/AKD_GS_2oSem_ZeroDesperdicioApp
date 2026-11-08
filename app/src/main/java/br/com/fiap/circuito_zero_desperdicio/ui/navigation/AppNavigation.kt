package br.com.fiap.circuito_zero_desperdicio.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.circuito_zero_desperdicio.ui.screens.details.DetailsView
import br.com.fiap.circuito_zero_desperdicio.ui.screens.home.HomeView
import br.com.fiap.circuito_zero_desperdicio.ui.screens.intro.IntroView
import br.com.fiap.circuito_zero_desperdicio.ui.screens.profile.PerfilView
import br.com.fiap.circuito_zero_desperdicio.ui.screens.resgates.ResgatesView
import br.com.fiap.circuito_zero_desperdicio.ui.screens.splash.SplashView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashView(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToIntro = {
                    navController.navigate("intro") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("intro") {
            IntroView(
                onFinishOnboarding = {
                    navController.navigate("home") {
                        popUpTo("intro") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeView(
                onNavigateToDetails = { idOferta -> navController.navigate("details/$idOferta") },
                onNavigateToResgates = { navController.navigate("resgates") },
                onNavigateToPerfil = { navController.navigate("perfil") }
            )
        }

        composable("details/{ofertaId}") { backStackEntry ->
            val ofertaId = backStackEntry.arguments?.getString("ofertaId") ?: ""

            DetailsView(
                ofertaId = ofertaId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("resgates") {
            ResgatesView(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetails = { idOferta -> navController.navigate("details/$idOferta") }
            )
        }

        composable("perfil") {
            PerfilView(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("splash") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}