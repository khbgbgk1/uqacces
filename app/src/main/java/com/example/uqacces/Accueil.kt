package com.example.uqacces

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/*
* Ecran d'accueil avec le plan et barre de recherche (Arrivée).
*L'utilisateur peut zoomer/déplacer le plan
* */

@Composable
fun Accueil(
    onSearchClick: () -> Unit,
    startNodeName: String? = null,
    endNodeName: String? = null
) {
    // Contenu du champ (affiche la destination choisie si on revient plus tard)
    var arrivee by remember { mutableStateOf(TextFieldValue("")) }

    // Get the map data from our centralized object
    val universityMapData = UniversityMap.data

    // Find the shortest path if start and end node names are provided
    val path = if (startNodeName != null && endNodeName != null) {
        remember(universityMapData, startNodeName, endNodeName) {
            val startNode = universityMapData.nodes.find { it.name.equals(startNodeName, ignoreCase = true) }
            val endNode = universityMapData.nodes.find { it.name.equals(endNodeName, ignoreCase = true) }
            if (startNode != null && endNode != null) {
                findShortestPath(universityMapData, startNode.id, endNode.id)
            } else {
                emptyList()
            }
        }
    } else {
        // No path is calculated if no start or end node is provided
        emptyList()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars   // gère la barre système pour ne pas depasser
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            // MapView with path
            MapView(
                mapData = universityMapData,
                pathNodeIds = path,
                modifier = Modifier.fillMaxSize()
            )

            // Champ "Arrivée" cliquable -> ouvre l’écran Recherche
            OutlinedTextField(
                value = arrivee,
                onValueChange = { },  // Désactivé car non modifiable
                placeholder = { Text("Arrivée") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                enabled = false,    // empêche l’édition
                readOnly = true,    // empêche clavier
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
                    .fillMaxWidth(0.80f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                    .clickable { onSearchClick() }  // Ouvre page de recherche
            )
        }
    }
}
