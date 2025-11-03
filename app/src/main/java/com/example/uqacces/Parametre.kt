package com.example.uqacces

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uqacces.ui.theme.UQACCESTheme
import androidx.lifecycle.viewmodel.compose.viewModel

/*
* Ecran des paramètres de l'application
* Les valeurs sont stockées dans SettingsViewModel afin de rester en mémoire
* même lorsqu'on navigue entre les pages
* */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametreScreen(
    vm: SettingsViewModel,          // ViewModel partagé pour conserver les paramètres
    onBack: () -> Unit              // action exécutée lorsqu'on clique sur la flèche de retour
) {
    // Liste des choix de langue
    val languages = listOf("Français", "Anglais")
    // Gère l'ouverture du menu déroulant (langue)
    var langExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // Barre du haut avec flèche retour
            CenterAlignedTopAppBar(
                title = { Text("Paramètres", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            //Paramètres sous forme de switches
            SettingRow("Ascenseur", vm.ascenseur) { vm.ascenseur = it }
            Divider()

            SettingRow("Escalier", vm.escalier) { vm.escalier = it }
            Divider()

            SettingRow("Mode hiver", vm.modeHiver) { vm.modeHiver = it }
            Divider()

            Spacer(Modifier.height(16.dp))

            // Sélecteur de langue
            Text(
                text = "Langues",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = langExpanded,
                onExpandedChange = { langExpanded = !langExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Champ affichant la langue sélectionnée
                OutlinedTextField(
                    value = vm.language,                 // state du VM
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = langExpanded)
                    }
                )
                // Menu déroulant avec choix des langues
                ExposedDropdownMenu(
                    expanded = langExpanded,
                    onDismissRequest = { langExpanded = false }
                ) {
                    languages.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                vm.language = option           // on écrit dans le VM
                                langExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Ligne d'un switch dans le menu paramètres
 * */
@Composable
private fun SettingRow(
    title: String,      // Texte du paramètre
    checked: Boolean,       // valeur actuelle du switch
    onCheckedChange: (Boolean) -> Unit      // callback de changement
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Titre du paramètre
        Text(text = title, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        // Switch de modification
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@SuppressLint("ViewModelConstructorInComposable")      // Je sais pas ce que signifie cette ligne juste ça fonctionnait pas sans mais ya genre 20 minutes je fonctionnais sans donc jsp
@Preview(showSystemUi = true)
@Composable
private fun ParametrePreview() {
    UQACCESTheme {
        // aperçu avec un VM temporaire
        ParametreScreen(vm = SettingsViewModel(), onBack = {})
    }
}
