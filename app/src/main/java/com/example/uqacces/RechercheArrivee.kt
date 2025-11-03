package com.example.uqacces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import com.example.uqacces.ui.theme.UQACCESTheme

/*
* Écran de recherche de l’arrivée
* */

private val POI_SAMPLES = listOf(
    "Accueil", "Bibliothèque", "Cafétéria", "Secrétariat",
    "Centre social", "Ascenseur Nord", "Ascenseur Sud"
)

private val PROF_SAMPLES = listOf(
    "Pr. Tremblay", "Pr. Gagnon", "Pr. Côté", "Pr. Lavoie",
    "Pr. Bouchard", "Pr. Morin", "Pr. Fortin", "Pr. Gauthier"
)


@Composable
fun RechercheArriveeScreen(
    onBack: () -> Unit = {},    // action de la flèche (retour à l’écran précédent)
    onSubmit: (String) -> Unit = {}   // ce callback ouvre Arrive.kt avec le texte
) {
    // Etat du champ de recherche
    var query by remember { mutableStateOf(TextFieldValue("")) }
    // Onglet sélectionné : 0 = point d'interet, 1 = Profs
    var selectedTab by remember { mutableStateOf(0) } // 0 = POI, 1 = Profs
    // Historique local des recherches (récent) (a refaire)
    val recents = remember { mutableStateListOf<String>() }

    // Liste courante selon l’onglet
    val currentList = if (selectedTab == 0) POI_SAMPLES else PROF_SAMPLES
    // Filtrage en mémoire en fonction de la saisie (a développer)
    val filtered = remember(query.text, selectedTab) {
        if (query.text.isBlank()) currentList
        else currentList.filter { it.contains(query.text.trim(), ignoreCase = true) }
    }

    // Valide une recherche : alimente "Récent" puis navigue
    fun submitText(text: String) {
        if (text.isBlank()) return
        if (!recents.contains(text)) recents.add(text) // alimente la section Récent
        onSubmit(text)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {

            // Barre "Votre recherche"
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Votre recherche") },
                // Icône gauche : flèche retour
                leadingIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                // Icône droite : caméra (peut être mettre detection d'image)
                trailingIcon = {
                    IconButton(onClick = { /* Caméra plus tard */ }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Caméra")
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                // Le clavier affiche "Search" et la touche lance la navigation
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        submitText(query.text.trim())   // Entrée => ouvrir Arrive
                    }
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            )

            //  Onglets Point d'interet et Professeur
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Points d’intérêt") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Professeurs") }
                )
            }

            // Liste : Récent + éléments filtrés
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // Section "Récent" si non vide
                if (recents.isNotEmpty()) {
                    item { SectionTitle("Récent") }
                    items(recents.reversed()) { item ->
                        ListRow(
                            label = item,
                            leading = { Icon(Icons.Default.History, null) },
                            onClick = { submitText(item) } // clic récent => ouvrir Arrive
                        )
                    }
                    item { Divider() }
                }
                // Titre de la liste selon l’onglet
                item { Spacer(Modifier.height(4.dp)); SectionTitle(if (selectedTab == 0) "Points d’intérêt" else "Professeurs") }

                // Éléments filtrés pour quand on tape quelque chose
                items(filtered) { item ->
                    ListRow(
                        label = item,
                        onClick = { submitText(item) }   // clic item => ouvrir Arrive
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
/**
 * Ligne cliquable pour un élément de recherche, utilisée pour afficher chaque résultat
 */

@Composable
private fun ListRow(
    label: String,      // Texte affiché dans la ligne
    leading: @Composable (() -> Unit)? = null,      // Icône optionnelle affichée à gauche (ex : horloge pour "Récent")
    onClick: () -> Unit         // Action exécutée lorsqu'on touche la ligne (ouvre la page de destination)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()         // La ligne prend toute la largeur
            .clickable { onClick() }        // Toute la surface est cliquable
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Si une icône de début existe (ex: horloge dans "Récent")
        if (leading != null) {
            leading()           // On dessine l’icône passée en paramètre
            Spacer(Modifier.width(12.dp))
        }
        // Texte principal de la ligne (ex: "Bibliothèque")
        Text(text = label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
    Divider()
}

@Preview(showSystemUi = true)
@Composable
private fun RechercheArriveePreview() {
    UQACCESTheme { RechercheArriveeScreen() }
}
