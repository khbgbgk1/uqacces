package com.example.uqacces

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.uqacces.ui.theme.UQACCESTheme


/*
* Ecran d'itinéraire
* Affiche les champs de départ" et d'arrivée en lecture seule
* Montre le plan en arrière-plan
* Barre du bas avec Réglages et Partager
* */
@Composable
fun ItineraireScreen(
    depart: String,     //  point de départ choisi
    arrivee: String,        //point d'arrivée choisi
    onEditDepart: () -> Unit,   // appelé quand on clique sur la croix du champ de départ (retour pour modifier)
    onEditArrivee: () -> Unit,      // appelé quand on clique sur la croix du champ d'arrivée (retour pour modifier)
    onSettings: () -> Unit = {}     // ouvre la page Paramètres
) {
    val context = LocalContext.current

    // Gestion du Zoom / Pan du plan
    var scale by remember { mutableStateOf(2.5f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    // Détection des gestes doigts pour zoom
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale = (scale * zoom).coerceIn(1f, 5f)
            offset += pan
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // pas de padding auto -> bottom bar collée tout en bas
        contentWindowInsets = WindowInsets(0),
        // Barre du bas (fixe)
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Bouton "Réglages" -> ouvre l'écran Paramètres
                FilledTonalButton(
                    onClick = onSettings,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Réglages")
                }
                // Bouton "Partager" -> partage textuel du trajet via Android Sharesheet
                FilledTonalButton(
                    onClick = {
                        val text = "Trajet: Départ = $depart → Arrivée = $arrivee"
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
    ) { inner ->

        // Empilement : image en fond + panneau blanc au premier plan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner) // (0 grâce à WindowInsets(0))
        ) {

            // fond : plan zoomable
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .then(gestureModifier)  // applique la détection de gestes
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plan_1),
                    contentDescription = "Plan de classe",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .align(Alignment.CenterStart)
                )
            }

            //PREMIER PLAN : panneau blanc avec champs + boutons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .statusBarsPadding()              // évite la superposition avec l'horloge/encoche
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .zIndex(1f)                        // au-dessus de l'image
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Champ "Départ" (lecture seule) + croix pour modifier
                        OutlinedTextField(
                            value = TextFieldValue(depart),
                            onValueChange = {  },
                            label = { Text("Départ") },
                            singleLine = true,
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = onEditDepart) {
                                    Icon(Icons.Default.Close, "Modifier départ")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(10.dp))

                        // Champ "Arrivée" (lecture seule) + croix pour modifier
                        OutlinedTextField(
                            value = TextFieldValue(arrivee),
                            onValueChange = {  },
                            label = { Text("Arrivée") },
                            singleLine = true,
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = onEditArrivee) {
                                    Icon(Icons.Default.Close, "Modifier arrivée")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        // Barre de boutons numérotés : 0..4 (réservés pour mettre les autres etage dans le futures)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            (0..4).forEach { n ->
                                OutlinedButton(
                                    onClick = { /* A faire */ },
                                    modifier = Modifier.height(40.dp),
                                    shape = RoundedCornerShape(10.dp)
                                ) { Text(n.toString()) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewItineraire() {
    UQACCESTheme {
        ItineraireScreen(
            depart = "Bibliothèque",
            arrivee = "Salle de classe",
            onEditDepart = {},
            onEditArrivee = {}
        )
    }
}
