package com.example.uqacces

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Accueil(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEditDepart: () -> Unit,
    onEditArrivee: () -> Unit,
    startNodeName: String? = null,
    endNodeName: String? = null
) {
    val context = LocalContext.current
    val universityMapData = UniversityMap.data

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
    var scale by remember { mutableStateOf(2.5f) }  // zoom de base
    var offset by remember { mutableStateOf(Offset.Zero) }  // position de l’image

    // Détection des gestes multitouch (zoom + pan)
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale = (scale * zoom).coerceIn(1f, 5f)        // limite zoom min/max
            offset += pan      // déplacement
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (startNodeName != null && endNodeName != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            val text = "Trajet: Départ = $startNodeName → Arrivée = $endNodeName"
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
        Box(modifier = Modifier.fillMaxSize()
            .padding(inner)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(gestureModifier)      // applique les gestes
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plan_1),
                    contentDescription = "Plan de classe",
                    contentScale = ContentScale.FillHeight,     // hauteur max
                    modifier = Modifier
                        .zIndex(0f)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .align(Alignment.CenterStart)
                )
                MapView(
                    mapData = universityMapData,
                    pathNodeIds = path,
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f) // Dessiné au-dessus
                        .align(Alignment.CenterStart)
                    // *** IMPORTANT : ENLEVER le graphicsLayer ici, le parent le gère. ***
                )
            }
//            MapView(
//                mapData = universityMapData,
//                pathNodeIds = path,
//                modifier = Modifier.fillMaxSize()
//                    .zIndex(1f)
//                    .align(Alignment.CenterStart)
//            )

            if (startNodeName == null || endNodeName == null) {
                OutlinedTextField(
                    value = TextFieldValue(""),
                    onValueChange = {},
                    placeholder = { Text("Arrivée") },
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = startNodeName.replace("Classe ", ""),
                            onValueChange = {},
                            label = { Text("Départ") },
                            singleLine = true,
                            readOnly = true,
                            trailingIcon = { IconButton(onClick = onEditDepart) { Icon(Icons.Default.Close, "Modifier départ") } },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = endNodeName.replace("Classe ", ""),
                            onValueChange = {},
                            label = { Text("Arrivée") },
                            singleLine = true,
                            readOnly = true,
                            trailingIcon = { IconButton(onClick = onEditArrivee) { Icon(Icons.Default.Close, "Modifier arrivée") } },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
