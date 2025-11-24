package com.example.uqacces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class SearchResult(val displayName: String, val nodeName: String)


private fun getSearchResults(query: String, mapData: MapData, exclude: String = ""): List<SearchResult> {
    val nodes = mapData.nodes
    val professors = mapData.professors

    if (query.isBlank()) {
        val pois = nodes.filter { it.type != "Classe" && it.type != "Corridor" && it.name != exclude }.map { SearchResult(it.name, it.name) }
        val profs = professors.mapNotNull { prof ->
            val roomName = nodes.find { it.id == prof.officeNodeId }?.name ?: ""
            if (roomName != exclude) SearchResult(prof.name, roomName) else null
        }
        return pois + profs
    }

    val classroomResults = nodes
        .filter { it.type == "Classe" && it.name.replace("Classe ", "").contains(query, ignoreCase = true) && it.name != exclude }
        .map { SearchResult(it.name.replace("Classe ", ""), it.name) }

    val profResults = professors
        .filter { it.name.contains(query, ignoreCase = true) }
        .mapNotNull { prof ->
            val roomName = nodes.find { it.id == prof.officeNodeId }?.name ?: ""
            if (roomName != exclude) SearchResult("${prof.name} (${roomName.replace("Classe ", "")})", roomName) else null
        }

    val poiResults = nodes
        .filter { it.type != "Classe" && it.type != "Corridor" && it.name.contains(query, ignoreCase = true) && it.name != exclude }
        .map { SearchResult(it.name, it.name) }

    return (profResults + classroomResults + poiResults).distinct()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointArriveeScreen(
    onBack: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedNodeName by remember { mutableStateOf<String?>(null) }

    val searchResults = getSearchResults(query, UniversityMap.data)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choisir l'arrivée") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour") } }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = { selectedNodeName?.let(onSubmit) },
                    enabled = selectedNodeName != null,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Suivant")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(horizontal = 16.dp)) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    selectedNodeName = null
                },
                label = { Text("Rechercher un lieu, un prof...") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                singleLine = true
            )
            LazyColumn {
                val profs = searchResults.filter { it.displayName.contains("(") || UniversityMap.data.professors.any { p -> p.name == it.displayName } }
                val salles = searchResults.filter { it.displayName.startsWith("P1-") }
                val pois = searchResults.filterNot { profs.contains(it) || salles.contains(it) }

                if (pois.isNotEmpty()) {
                    item { Text("Points d'intérêt", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) }
                    items(pois) { result ->
                        Text(
                            text = result.displayName,
                            modifier = Modifier.fillMaxWidth().clickable { query = result.displayName; selectedNodeName = result.nodeName }.padding(vertical = 12.dp)
                        )
                    }
                }
                if (profs.isNotEmpty()) {
                    item { Text("Professeurs", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) }
                    items(profs) { result ->
                        Text(
                            text = result.displayName,
                            modifier = Modifier.fillMaxWidth().clickable { query = result.displayName; selectedNodeName = result.nodeName }.padding(vertical = 12.dp)
                        )
                    }
                }
                if (salles.isNotEmpty()) {
                    item { Text("Salles", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) }
                    items(salles) { result ->
                        Text(
                            text = result.displayName,
                            modifier = Modifier.fillMaxWidth().clickable { query = result.displayName; selectedNodeName = result.nodeName }.padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointDepartScreen(
    initialDepart: String,
    initialArrive: String,
    onBack: () -> Unit,
    onDone: (String, String) -> Unit
) {
    var departQuery by remember { mutableStateOf(initialDepart) }
    var selectedNodeName by remember { mutableStateOf<String?>(null) }
    
    val searchResults = getSearchResults(departQuery, UniversityMap.data, exclude = initialArrive)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choisir le départ") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour") } }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = { selectedNodeName?.let { onDone(it, initialArrive) } },
                    enabled = selectedNodeName != null,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Trouver le chemin")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(horizontal = 16.dp)) {
            TextField(
                value = initialArrive.replace("Classe ", ""),
                onValueChange = {},
                label = { Text("Point d'arrivée") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                readOnly = true, enabled = false
            )
            TextField(
                value = departQuery,
                onValueChange = { departQuery = it; selectedNodeName = null },
                label = { Text("Rechercher un point de départ") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                val profs = searchResults.filter { it.displayName.contains("(") || UniversityMap.data.professors.any { p -> p.name == it.displayName } }
                val salles = searchResults.filter { it.displayName.startsWith("P1-") }
                val pois = searchResults.filterNot { profs.contains(it) || salles.contains(it) }

                if (pois.isNotEmpty()) {
                    item { Text("Points d'intérêt", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) }
                    items(pois) { result ->
                        Text(
                            text = result.displayName,
                            modifier = Modifier.fillMaxWidth().clickable { departQuery = result.displayName; selectedNodeName = result.nodeName }.padding(vertical = 12.dp)
                        )
                    }
                }
                if (profs.isNotEmpty()) {
                    item { Text("Professeurs", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) }
                    items(profs) { result ->
                        Text(
                            text = result.displayName,
                            modifier = Modifier.fillMaxWidth().clickable { departQuery = result.displayName; selectedNodeName = result.nodeName }.padding(vertical = 12.dp)
                        )
                    }
                }
                if (salles.isNotEmpty()) {
                    item { Text("Salles", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) }
                    items(salles) { result ->
                        Text(
                            text = result.displayName,
                            modifier = Modifier.fillMaxWidth().clickable { departQuery = result.displayName; selectedNodeName = result.nodeName }.padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}
