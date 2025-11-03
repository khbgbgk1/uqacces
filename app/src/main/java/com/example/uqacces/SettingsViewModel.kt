package com.example.uqacces

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    // états conservés tant que l’activité vit
    var ascenseur by mutableStateOf(true)
    var escalier by mutableStateOf(true)
    var modeHiver by mutableStateOf(false)

    var language by mutableStateOf("Français") // "Français" ou "Anglais"
}
