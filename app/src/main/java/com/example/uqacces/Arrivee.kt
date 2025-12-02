package com.example.uqacces


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.uqacces.ui.theme.UQACCESTheme

/**
 * Ecran affiché après avoir choisi l'arrivée dans RechercheArrivee
*/
@Composable
fun ArriveScreen(
    query: String,      //texte choisi par l'utilisateur
    onBackToSearch: () -> Unit,     // Appelé quand on touche l'icone en haut -> retour à l'écran de recherche
    onYAller: () -> Unit = {}       // Bouton "Y-aller" pour passer à l'étape suivante
) {
    // Zoom & déplacement sur l’image (même comportement que Accueil.kt)
    var scale by remember { mutableStateOf(2.5f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Détection des gestes doigts pour zoom
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale = (scale * zoom).coerceIn(1f, 5f)     // limite zoom entre ×1 et ×5
            offset += pan
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            // Image zoomable (identique à Accueil)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(gestureModifier)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ppplan_1),
                    contentDescription = "Plan de classe",
                    contentScale = ContentScale.FillHeight,
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

            //  Barre "Votre recherche" (lecture seule) -> click = retour
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f) // assure qu'elle reste au-dessus du plan zoomable
                    .padding(top = 40.dp)
                    .fillMaxWidth(0.80f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                    .clickable { onBackToSearch() } // clic = retour à la recherche
            ) {
                OutlinedTextField(
                    value = TextFieldValue(query),      // affiche le lieu sélectionné
                    onValueChange = {  },        // non modifiable
                    placeholder = { Text("Votre recherche") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }



            //  Bouton "Y-aller" en bas
            Button(
                onClick = onYAller,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 28.dp)
                    .fillMaxWidth(0.55f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Y-aller!")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ArrivePreview() {
    UQACCESTheme {
        ArriveScreen(
            query = "Bibliothèque",
            onBackToSearch = {}
        )
    }
}
