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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UQACCESTheme {
                // Contrôleur de navigation
                val navController = rememberNavController()

                // ViewModel partagé à l’échelle de l’activité
                // -> garde l'état des paramètres même si on change d'écran
                val settingsVM: SettingsViewModel = viewModel()

                NavHost(navController, startDestination = "accueil") {

                    // Ecran d'accueil
                    composable("accueil") {
                        // Clique -> aller à la recherche d'arrivée
                        Accueil(onSearchClick = { navController.navigate("recherche") })
                    }

                    // Ecran recherche du lieu d'arrivée
                    composable("recherche") {
                        RechercheArriveeScreen(
                            onBack = { navController.popBackStack() }, // Retour à accueil
                            onSubmit = { text ->
                                // On encode le texte(l'arrivée) pour le passer dans l'URL
                                val encoded = java.net.URLEncoder.encode(text, "UTF-8")
                                navController.navigate("arrive/$encoded")
                            }
                        )
                    }

                    // Ecran Affichage de l'arrivée
                    composable("arrive/{query}") { backStackEntry ->
                        // On récupère et décode le texte recherché
                        val q = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("query").orEmpty(),
                            "UTF-8"
                        )
                        ArriveScreen(
                            query = q,
                            onBackToSearch = { navController.popBackStack() },   // Retour recherche
                            onYAller = {
                                // Arrivée choisie -> on passe à la recherche du départ
                                val d = java.net.URLEncoder.encode("", "UTF-8") // départ vide
                                val a = java.net.URLEncoder.encode(q, "UTF-8")  // arrivée choisie
                                navController.navigate("rechercheDepart/$d/$a")
                            }
                        )
                    }

                    // Ecran Recherche du départ
                    composable("rechercheDepart/{depart}/{arrive}") { backStackEntry ->
                        // on récupère les valeurs passées dans l'URL pour préremplir les champs
                        val d = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("depart").orEmpty(), "UTF-8"
                        )
                        val a = java.net.URLDecoder.decode(
                            backStackEntry.arguments?.getString("arrive").orEmpty(), "UTF-8"
                        )
                        // On ouvre l'écran de recherche du départ, en lui donnant les valeurs actuelles
                        RechercheDepartScreen(
                            initialDepart = d,
                            initialArrive = a,
                            onBack = { navController.popBackStack() },  // Retour résultats arrivée
                            // Lorsque l'utilisateur valide son choix
                            onDone = { depart, arrive ->
                                val ed = java.net.URLEncoder.encode(depart, "UTF-8")
                                val ea = java.net.URLEncoder.encode(arrive, "UTF-8")
                                // On ne va à l'itinéraire que si les deux champs sont remplis
                                if (depart.isNotBlank() && arrive.isNotBlank()) {
                                    navController.navigate("itineraire/$ed/$ea")
                                }
                            }
                        )
                    }

                    // Ecran d'itinéraire
                    composable("itineraire/{depart}/{arrivee}") { entry ->
                        val d = java.net.URLDecoder.decode(
                            entry.arguments?.getString("depart").orEmpty(), "UTF-8"
                        )
                        val a = java.net.URLDecoder.decode(
                            entry.arguments?.getString("arrivee").orEmpty(), "UTF-8"
                        )
                        ItineraireScreen(
                            depart = d,
                            arrivee = a,
                            // Bouton "X" du champ Départ → permet d'en choisir un nouveau
                            onEditDepart = {
                                val newD = java.net.URLEncoder.encode("", "UTF-8") // reset départ
                                val newA = java.net.URLEncoder.encode(a, "UTF-8")   // garde arrivée
                                navController.navigate("rechercheDepart/$newD/$newA")
                            },
                            // Bouton "X" du champ Arrivée → permet d'en choisir une nouvelle
                            onEditArrivee = {
                                val newD = java.net.URLEncoder.encode(d, "UTF-8")   // garde départ
                                val newA = java.net.URLEncoder.encode("", "UTF-8")  // reset arrivée
                                navController.navigate("rechercheDepart/$newD/$newA")
                            },
                            // Ouvre la page des paramètres
                            onSettings = { navController.navigate("parametres") }
                        )
                    }

                    //  Ecran Paramètres
                    composable("parametres") {
                        ParametreScreen(
                            vm = settingsVM,    // On passe le ViewModel pour conserver les etats des boutons
                            onBack = { navController.popBackStack() }   // Retour à l'itinéraire
                        )
                    }
                }
            }
        }
    }
}
