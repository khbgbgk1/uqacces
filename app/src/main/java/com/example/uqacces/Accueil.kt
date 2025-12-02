package com.example.uqacces

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Accueil(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEditDepart: () -> Unit,
    onEditArrivee: () -> Unit,
    onSwap: () -> Unit,
    startNodeName: String? = null,
    endNodeName: String? = null,
    debug: Boolean = false,
) {
    val context = LocalContext.current
    val universityMapData = UniversityMap.data
    val heading by rememberCompassHeading()

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
        emptyList()
    }

    fun getDisplayName(nodeName: String?): String {
        if (nodeName == null) return ""
        val node = universityMapData.nodes.find { it.name.equals(nodeName, ignoreCase = true) } ?: return nodeName.replace("Classe ", "")

        // Chercher si c'est le bureau d'un prof
        val professor = universityMapData.professors.find { it.officeNodeId == node.id }
        if (professor != null) {
            val roomName = professor.officeNodeId
            val roomNumber = roomName.replace("Classe ", "")
            return "${professor.name} ($roomNumber)"
        }

        // Chercher si c'est un point d'intérêt
        val poi = universityMapData.poi.find { it.nodeId == node.id }
        if (poi != null) {
            return poi.name
        }

        // Sinon, retourner le nom du noeud nettoyé
        return nodeName.replace("Classe ", "")
    }

    val displayStartName = getDisplayName(startNodeName)
    val displayEndName = getDisplayName(endNodeName)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (startNodeName != null && endNodeName != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp) //modifier pour remonter la barre en bas
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FilledTonalButton(onClick = onSettingsClick, shape = RoundedCornerShape(14.dp)) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Réglages")
                    }
                    FilledTonalButton(
                        onClick = {
                            val text = "Trajet: Départ = $displayStartName → Arrivée = $displayEndName"
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            }
                            context.startActivity(Intent.createChooser(intent, "Partager le trajet"))
                        },
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Partager")
                    }
                }
            }
        }
    ) { inner ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(inner)) {

            MapView(
                mapData = universityMapData,
                mapBackground = UniversityMap.background,
                pathNodeIds = path,
                debugNodes = debug,
                heading = heading,
                modifier = Modifier.fillMaxSize()
            )

            if (startNodeName == null || endNodeName == null) {
                OutlinedTextField(
                    value = TextFieldValue(""),
                    onValueChange = {},
                    placeholder = { Text("Destination") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(28.dp),
                    enabled = false,
                    readOnly = true,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 40.dp)
                        .fillMaxWidth(0.80f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                        .clickable { onSearchClick() }
                )
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .zIndex(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    // CHANGEMENT : Utiliser un Row pour aligner les champs (Colonne) et l'icône (Bouton)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically // Assure que le bouton est centré verticalement
                    ) {
                        Column( // Conteneur pour les deux OutlinedTextFields
                            modifier = Modifier.weight(1f) // Prend l'espace disponible
                        ) {
                            OutlinedTextField(
                                value = displayStartName,
                                onValueChange = {},
                                label = { Text("Départ") },
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = { IconButton(onClick = onEditDepart) { Icon(Icons.Default.Close, "Modifier départ") } },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(Modifier.height(10.dp))

                            OutlinedTextField(
                                value = displayEndName,
                                onValueChange = {},
                                label = { Text("Arrivée") },
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = { IconButton(onClick = onEditArrivee) { Icon(Icons.Default.Close, "Modifier arrivée") } },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // BOUTON D'INVERSION PLACÉ À CÔTÉ (dans le Row)
                        Spacer(Modifier.width(12.dp))
                        IconButton(
                            onClick = onSwap, // Appel de la fonction pour inverser les valeurs
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Filled.SwapVert,
                                contentDescription = "Inverser départ et arrivée",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            CompassView(heading = heading, modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 50.dp, end = 16.dp))
        }
    }
}

@Preview(showBackground = true, name = "Accueil - Trajet avec Debug")
@Composable
fun PreviewAccueilDebug() {
    Accueil(
        onSearchClick = {},
        onSettingsClick = {},
        onEditDepart = {},
        onEditArrivee = {},
        onSwap = {},
        debug = true // Afficher les nœuds en mode debug
    )
}
