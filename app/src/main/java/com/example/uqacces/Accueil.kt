package com.example.uqacces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/*
* Ecran d'accueil avec le plan et barre de recherche (Arrivée).
*L'utilisateur peut zoomer/déplacer le plan
* */


@Composable
fun Accueil(
    onSearchClick: () -> Unit
) {
    // Contenu du champ (affiche la destination choisie si on revient plus tard)
    var arrivee by remember { mutableStateOf(TextFieldValue("")) }

    // Etats du zoom et déplacement de l’image
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
        contentWindowInsets = WindowInsets.systemBars   // gère la barre système pour ne pas depasser
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            // IMAGE DU PLAN AVEC ZOOM
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
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .align(Alignment.CenterStart)
                )
            }

            // Champ "Arrivée" cliquable -> ouvre l’écran Recherche
            // Champ "Arrivée" cliquable mais non modifiable
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



