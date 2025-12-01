package com.example.uqacces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uqacces.ui.theme.UQACCESTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UQACCESTheme {
                val navController = rememberNavController()
                val settingsVM: SettingsViewModel = viewModel()

                NavHost(navController, startDestination = "accueil") {

                    // Default home screen (no path)
                    composable("accueil") {
                        Accueil(
                            onSearchClick = { navController.navigate("rechercheArrivee") },
                            onSettingsClick = { navController.navigate("parametres") },
                            onEditDepart = { /* Should not happen */ },
                            onEditArrivee = { /* Should not happen */ },
                            onSwap = {},
                        )
                    }

                    // Home screen with a path to display
                    composable(
                        "accueil/{startNodeName}/{endNodeName}",
                        arguments = listOf(
                            navArgument("startNodeName") { type = NavType.StringType },
                            navArgument("endNodeName") { type = NavType.StringType })
                    ) {
                        val startNodeName = it.arguments?.getString("startNodeName")?.let { name -> URLDecoder.decode(name, "UTF-8") } ?: ""
                        val endNodeName = it.arguments?.getString("endNodeName")?.let { name -> URLDecoder.decode(name, "UTF-8") } ?: ""
                        Accueil(
                            onSearchClick = { navController.navigate("rechercheArrivee") },
                            onSettingsClick = { navController.navigate("parametres") },
                            startNodeName = startNodeName,
                            endNodeName = endNodeName,
                            onEditDepart = {
                                val encodedArrivee = URLEncoder.encode(endNodeName, "UTF-8")
                                navController.navigate("rechercheDepart/${URLEncoder.encode("", "UTF-8")}/$encodedArrivee")
                            },
                            onEditArrivee = {
                                val encodedDepart = URLEncoder.encode(startNodeName, "UTF-8")
                                // Navigate to the arrival search screen, but pass the existing departure point
                                navController.navigate("rechercheArrivee/$encodedDepart")
                            },
                            onSwap = {navController.swapAndNavigateHome(
                                currentDepart = startNodeName,
                                currentArrivee = endNodeName,
                                currentRoute = "accueil/{startNodeName}/{endNodeName}"
                            )},
                        )
                    }

                    // Route for starting a NEW search (select arrival first)
                    composable("rechercheArrivee") {
                        PointArriveeScreen(
                            onBack = { navController.navigate("accueil") },
                            onSubmit = { arrivee ->
                                val encodedArrivee = URLEncoder.encode(arrivee, "UTF-8")
                                // Go to select departure
                                navController.navigate("rechercheDepart/${URLEncoder.encode("", "UTF-8")}/$encodedArrivee")
                            },
                            DestinationText = "Destination",
                        )
                    }

                    // Route for EDITING an arrival (departure is already known)
                    composable(
                        "rechercheArrivee/{depart}",
                        arguments = listOf(navArgument("depart") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val depart = URLDecoder.decode(backStackEntry.arguments?.getString("depart").orEmpty(), "UTF-8")
                        PointArriveeScreen(
                            onBack = { navController.navigate("accueil") },
                            onSubmit = { arrivee ->
                                // We now have both depart and arrivee, go straight to map
                                val encodedDepart = URLEncoder.encode(depart, "UTF-8")
                                val encodedArrivee = URLEncoder.encode(arrivee, "UTF-8")
                                navController.navigate("accueil/$encodedDepart/$encodedArrivee") {
                                    popUpTo("accueil") { inclusive = true }
                                }
                            },
                            DestinationText = "Destination",
                        )
                    }

                    composable("rechercheDepart/{depart}/{arrive}") { backStackEntry ->
                        val d = URLDecoder.decode(backStackEntry.arguments?.getString("depart").orEmpty(), "UTF-8")
                        val a = URLDecoder.decode(backStackEntry.arguments?.getString("arrive").orEmpty(), "UTF-8")
                        PointDepartScreen(
                            initialDepart = d,
                            initialArrive = a,
                            onBack = { navController.navigate("accueil") },
                            onDone = { depart, arrive ->
                                if (depart.isNotBlank() && arrive.isNotBlank()) {
                                    val encodedDepart = URLEncoder.encode(depart, "UTF-8")
                                    val encodedArrive = URLEncoder.encode(arrive, "UTF-8")
                                    navController.navigate("accueil/$encodedDepart/$encodedArrive") {
                                        popUpTo("accueil") { inclusive = true }
                                    }
                                }
                            },
                            DestinationText = "Point de départ",
                        )
                    }

                    composable("parametres") {
                        ParametreScreen(
                            vm = settingsVM,
                            onBack = { navController.popBackStack() }   // Retour à l'itinéraire
                        )
                    }
                }
            }
        }
    }
}

fun NavController.swapAndNavigateHome(
    currentDepart: String,
    currentArrivee: String,
    currentRoute: String
) {
    // 1. Permuter les valeurs
    val newDepart = currentArrivee
    val newArrivee = currentDepart

    // 2. Encoder
    val encodedNewDepart = URLEncoder.encode(newDepart, "UTF-8")
    val encodedNewArrivee = URLEncoder.encode(newArrivee, "UTF-8")

    // 3. Naviguer vers le nouveau chemin
    this.navigate("accueil/$encodedNewDepart/$encodedNewArrivee") {
        // Remplacer l'entrée actuelle dans la pile de navigation
        popUpTo(currentRoute) { inclusive = true }
    }
}