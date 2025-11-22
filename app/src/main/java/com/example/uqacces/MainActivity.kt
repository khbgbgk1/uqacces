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
                        Accueil(onSearchClick = { navController.navigate("recherche") })
                    }

                    // Home screen with a path to display
                    composable(
                        "accueil/{startNodeName}/{endNodeName}",
                        arguments = listOf(
                            navArgument("startNodeName") { type = NavType.StringType; nullable = true },
                            navArgument("endNodeName") { type = NavType.StringType; nullable = true })
                    ) {
                        val startNodeName = it.arguments?.getString("startNodeName")?.let { name -> URLDecoder.decode(name, "UTF-8") }
                        val endNodeName = it.arguments?.getString("endNodeName")?.let { name -> URLDecoder.decode(name, "UTF-8") }
                        Accueil(
                            onSearchClick = { navController.navigate("recherche") },
                            startNodeName = startNodeName,
                            endNodeName = endNodeName
                        )
                    }

                    composable("recherche") {
                        RechercheArriveeScreen(
                            onBack = { navController.popBackStack() },
                            onSubmit = { text ->
                                val encoded = URLEncoder.encode(text, "UTF-8")
                                navController.navigate("arrive/$encoded")
                            }
                        )
                    }

                    composable("arrive/{query}") { backStackEntry ->
                        val q = URLDecoder.decode(backStackEntry.arguments?.getString("query").orEmpty(), "UTF-8")
                        ArriveScreen(
                            query = q,
                            onBackToSearch = { navController.popBackStack() },
                            onYAller = {
                                val d = URLEncoder.encode("", "UTF-8")
                                val a = URLEncoder.encode(q, "UTF-8")
                                navController.navigate("rechercheDepart/$d/$a")
                            }
                        )
                    }

                    composable("rechercheDepart/{depart}/{arrive}") { backStackEntry ->
                        val d = URLDecoder.decode(backStackEntry.arguments?.getString("depart").orEmpty(), "UTF-8")
                        val a = URLDecoder.decode(backStackEntry.arguments?.getString("arrive").orEmpty(), "UTF-8")
                        RechercheDepartScreen(
                            initialDepart = d,
                            initialArrive = a,
                            onBack = { navController.popBackStack() },
                            onDone = { depart, arrive ->
                                if (depart.isNotBlank() && arrive.isNotBlank()) {
                                    val encodedDepart = URLEncoder.encode(depart, "UTF-8")
                                    val encodedArrive = URLEncoder.encode(arrive, "UTF-8")
                                    navController.navigate("accueil/$encodedDepart/$encodedArrive")
                                }
                            }
                        )
                    }

                    composable("parametres") {
                        ParametreScreen(
                            vm = settingsVM,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
