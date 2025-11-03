package com.example.uqacces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import com.example.uqacces.ui.theme.UQACCESTheme

/*
* Ecran où l'on choisit le départ et on peut modifier l'arrivée
*/

// Liste d'exemples : Points d'intérêt du campus
private val POI_SAMPLES = listOf(
    "Accueil / Réception", "Bibliothèque", "Cafétéria", "Secrétariat",
    "Centre social", "Ascenseur Nord", "Ascenseur Sud", "Station STS"
)

// Liste d'exemples : Professeurs du campus (generer par ia)
private val PROF_SAMPLES = listOf(
    "Pr. Tremblay", "Pr. Gagnon", "Pr. Côté", "Pr. Lavoie",
    "Pr. Bouchard", "Pr. Morin", "Pr. Fortin", "Pr. Gauthier"
)

private enum class ActiveField { DEPART, ARRIVE }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RechercheDepartScreen(
    initialDepart: String = "",     // texte d'arrivée venant de ArriveScreen
    initialArrive: String,
    onBack: () -> Unit = {},        // bouton retour (flèche)
    onDone: (depart: String, arrive: String) -> Unit = { _, _ -> } // Valider les deux champs
) {
    var depart by remember { mutableStateOf(TextFieldValue(initialDepart)) }
    var arrive by remember { mutableStateOf(TextFieldValue(initialArrive)) }
    // Indique quel champ prend la sélection depuis la liste
    var active by remember { mutableStateOf(ActiveField.DEPART) }

    // Onglet actif : 0 = Poitn d'interet, 1 = Professeurs
    var selectedTab by remember { mutableStateOf(0) } // 0 = POI, 1 = Profs
    val list = if (selectedTab == 0) POI_SAMPLES else PROF_SAMPLES

    /**
     * Quand l'utilisateur choisit un item dans la liste
     * On remplit le champ actif (Départ / Arrivée)
     * Et on valide automatiquement si les 2 champs sont remplis
     */
    fun applyFromList(choice: String) {
        // Met à jour le champ selectionné :
        when (active) {
            ActiveField.DEPART -> depart = TextFieldValue(choice)
            ActiveField.ARRIVE -> arrive = TextFieldValue(choice)
        }

        // Valeurs à jour après le clic
        val d = (if (active == ActiveField.DEPART) choice else depart.text).trim()
        val a = (if (active == ActiveField.ARRIVE) choice else arrive.text).trim()

        // Si les deux champs sont remplis, on passe à l'écran suivant
        if (d.isNotEmpty() && a.isNotEmpty()) {
            onDone(d, a)
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sélection du trajet") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars
    ) { inner ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp)
        ) {


            Spacer(Modifier.height(8.dp))
            // Champ Départ
            OutlinedTextField(
                value = depart,
                onValueChange = { depart = it },
                placeholder = { Text("Choix de départ") },
                singleLine = true,
                // Si Arrivée est vide -> Next, sinon -> Done (afin que Entrée navigue)
                keyboardOptions = KeyboardOptions(
                    imeAction = if (arrive.text.isBlank()) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = { active = ActiveField.ARRIVE },
                    onDone = {
                        // on va à la page Naviguer si les deux champs sont remplis
                        val d = depart.text.trim()
                        val a = arrive.text.trim()
                        if (d.isNotEmpty() && a.isNotEmpty()) onDone(d, a)
                    }
                ),
                trailingIcon = {
                    if (depart.text.isNotEmpty()) {
                        IconButton(onClick = { depart = TextFieldValue("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Effacer départ")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { if (it.isFocused) active = ActiveField.DEPART }
            )

            Spacer(Modifier.height(12.dp))

            // Champ Arrivée
            OutlinedTextField(
                value = arrive,
                onValueChange = { arrive = it }, // modifiable après effacement
                placeholder = { Text("Arrivée") },
                singleLine = true,
                // Si Départ est vide -> Next, sinon -> Done
                keyboardOptions = KeyboardOptions(
                    imeAction = if (depart.text.isBlank()) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = { active = ActiveField.DEPART },
                    onDone = {
                        val d = depart.text.trim()
                        val a = arrive.text.trim()
                        if (d.isNotEmpty() && a.isNotEmpty()) onDone(d, a)
                    }
                ),
                trailingIcon = {
                    if (arrive.text.isNotEmpty()) {
                        IconButton(onClick = { arrive = TextFieldValue("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Effacer arrivée")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { if (it.isFocused) active = ActiveField.ARRIVE }
            )

            Spacer(Modifier.height(16.dp))

            // Onglets : Points d'intérêt/Professeurs
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

            // Liste des suggestions
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                /** Option spéciale : votre position actuelle */
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { applyFromList("Votre position") }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("• Votre position", style = MaterialTheme.typography.bodyLarge)
                    }
                    Divider()
                }

                /** Items de la liste */
                items(list) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { applyFromList(item) }
                            .padding(vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item, style = MaterialTheme.typography.bodyLarge)
                    }
                    Divider()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewRechercheDepart() {
    UQACCESTheme {
        RechercheDepartScreen(initialArrive = "Bibliothèque")
    }
}
