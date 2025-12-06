package com.example.uqacces

import android.content.Intent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable // NOUVEAU
import androidx.compose.animation.core.VectorConverter // NOUVEAU
import androidx.compose.animation.core.keyframes // NOUVEAU
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch // NOUVEAU

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

    // --- NOUVELLE LOGIQUE D'ANIMATION DE SECOUSSE ---
    val coroutineScope = rememberCoroutineScope()
    // Animatable pour l'offset horizontal (utilise Dp pour une meilleure compatibilité)
    val shakeOffset = remember { Animatable(0.dp, Dp.VectorConverter) }

    // Fonction pour déclencher l'animation de secousse
    fun triggerShakeAnimation() {
        coroutineScope.launch {
            // Lancer l'animation de secousse : -10dp, +10dp, -8dp, +6dp, puis retour à 0dp
            shakeOffset.animateTo(
                targetValue = 0.dp, // La valeur finale est le centre (0dp)
                animationSpec = keyframes {
                    durationMillis = 350 // Durée totale de la secousse
                    0.dp.at(0)
                    (-10).dp.at(50)    // Gauche
                    (10).dp.at(100)    // Droite
                    (-8).dp.at(150)    // Gauche (moins fort)
                    (6).dp.at(200)     // Droite (moins fort)
                    (-4).dp.at(250)    // Gauche (très léger)
                    0.dp.at(350)       // Retour à la position initiale
                }
            )
        }
    }
    // --------------------------------------------------

    val path = if (startNodeName != null && endNodeName != null) {
        // ... (Logique path existante)
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

    // ... (Logique getDisplayName existante) ...
    fun getDisplayName(nodeName: String?): String {
        if (nodeName == null) return ""
        val node = universityMapData.nodes.find { it.name.equals(nodeName, ignoreCase = true) } ?: return nodeName.replace("Classe ", "")

        val professor = universityMapData.professors.find { it.officeNodeId == node.id }
        if (professor != null) {
            val roomName = professor.officeNodeId
            val roomNumber = roomName.replace("Classe ", "")
            return "${professor.name} ($roomNumber)"
        }

        val poi = universityMapData.poi.find { it.nodeId == node.id }
        if (poi != null) {
            return poi.name
        }

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
                        .height(110.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FilledTonalButton(
                        onClick = {
                            val text = "Trajet: Départ = $displayStartName → Arrivée = $displayEndName"
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            }
                            context.startActivity(Intent.createChooser(intent, "Partager le trajet"))
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.padding(bottom = 40.dp)
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

            // APPEL DE MAPVIEW : Nous passons la fonction de secousse
            MapView(
                mapData = universityMapData,
                mapBackground = UniversityMap.background,
                pathNodeIds = path,
                debugNodes = debug,
                heading = heading,
                onMapInteraction = { triggerShakeAnimation() }, // Secousse au mouvement de carte
                modifier = Modifier.fillMaxSize()
            )

            if (startNodeName == null || endNodeName == null) {

                // MODIFICATION : Appliquer l'offset de secousse à la barre de recherche
                val offsetModifier = Modifier.offset(x = shakeOffset.value)

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
                        // MODE JOUR FORCÉ pour la visibilité
                        .background(Color.White.copy(alpha = 0.95f))
                        .clickable { onSearchClick() }
                        // APPLICATION DE L'ANIMATION DE SECOUSSE
                        .then(offsetModifier)
                )
            } else {
                Card(
                    // ... (La logique de Card pour les champs Départ/Arrivée reste inchangée)
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .zIndex(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    // ... (Contenu de la Card inchangé)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
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

                        Spacer(Modifier.width(12.dp))
                        IconButton(
                            onClick = onSwap,
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
