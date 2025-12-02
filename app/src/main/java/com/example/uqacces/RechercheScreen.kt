package com.example.uqacces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@Composable
fun PointArriveeScreen(
    onBack: () -> Unit,
    onSubmit: (String) -> Unit,
    DestinationText : String = "Destination"
) {
    var query by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) } // 0: POI, 1: Profs, 2: Salles
    val mapData = UniversityMap.data

    val submitAndNavigate = { destinationName: String ->
        if (destinationName.isNotBlank()) {
            onSubmit(destinationName)
        }
    }

    val pois = remember { mapData.poi }
    val profs = remember { mapData.professors }
    val salles = remember { mapData.nodes.filter { node -> node.type == "Classe" } }

    LaunchedEffect(query) {
        if (query.isNotBlank()) {
            // Priorité 1: Points d'intérêt
            if (pois.any { it.name.contains(query, ignoreCase = true) }) {
                if (selectedTab != 0) selectedTab = 0
                return@LaunchedEffect
            }
            // Priorité 2: Professeurs
            if (profs.any { it.name.contains(query, ignoreCase = true) }) {
                if (selectedTab != 1) selectedTab = 1
                return@LaunchedEffect
            }
            // Priorité 3: Salles
            if (salles.any { it.name.replace("Classe ", "").contains(query, ignoreCase = true) }) {
                if (selectedTab != 2) selectedTab = 2
                return@LaunchedEffect
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text(DestinationText) },
                leadingIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour") } },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { /* Could trigger search on first result */ }),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
            )

            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Points d'intérêt") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Professeurs") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Salles") })
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> { // Points of Interest
                        val filteredPois = pois.filter { poi -> poi.name.contains(query, ignoreCase = true) }
                        items(filteredPois) { poi ->
                            val nodeName = mapData.nodes.find { node -> node.id == poi.nodeId }?.name ?: "N/A"
                            ListRow(label = poi.name, onClick = { submitAndNavigate(nodeName) })
                        }
                    }
                    1 -> { // Professors
                        val filteredProfs = profs.filter { prof -> prof.name.contains(query, ignoreCase = true) }
                        items(filteredProfs) { prof ->
                            val roomName = mapData.nodes.find { room -> room.id == prof.officeNodeId }?.name ?: "N/A"
                            ListRow(
                                label = "${prof.name} ",
                                onClick = { submitAndNavigate(roomName) }
                            )
                        }
                    }
                    2 -> { // Rooms
                        val filteredSalles = salles.filter { salle -> salle.name.replace("Classe ", "").contains(query, ignoreCase = true) }
                        items(filteredSalles) { salle ->
                            ListRow(
                                label = salle.name.replace("Classe ", ""),
                                onClick = { submitAndNavigate(salle.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PointDepartScreen(
    initialDepart: String,
    initialArrive: String,
    onBack: () -> Unit,
    onDone: (String, String) -> Unit,
    DestinationText : String = "Point de départ"
) {
    var query by remember { mutableStateOf(initialDepart) }
    var selectedTab by remember { mutableStateOf(0) } // 0: POI, 1: Profs, 2: Salles
    val mapData = UniversityMap.data

    val submitAndNavigate = { departName: String ->
        if (departName.isNotBlank()) {
            onDone(departName, initialArrive)
        }
    }

    val pois = remember(initialArrive) { mapData.poi.filter { poi -> mapData.nodes.find { node -> node.id == poi.nodeId }?.name != initialArrive } }
    val profs = remember { mapData.professors }
    val salles = remember(initialArrive) { mapData.nodes.filter { n -> n.type == "Classe" && n.name != initialArrive } }

    LaunchedEffect(query) {
        if (query.isNotBlank()) {
            // Priorité 1: Points d'intérêt
            if (pois.any { it.name.contains(query, ignoreCase = true) }) {
                if (selectedTab != 0) selectedTab = 0
                return@LaunchedEffect
            }
            // Priorité 2: Professeurs
            if (profs.any { it.name.contains(query, ignoreCase = true) }) {
                if (selectedTab != 1) selectedTab = 1
                return@LaunchedEffect
            }
            // Priorité 3: Salles
            if (salles.any { it.name.replace("Classe ", "").contains(query, ignoreCase = true) }) {
                if (selectedTab != 2) selectedTab = 2
                return@LaunchedEffect
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text(DestinationText) },
                leadingIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour") } },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
            )

            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Points d'intérêt") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Professeurs") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Salles") })
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> { // POI
                        val filtered = pois.filter { p -> p.name.contains(query, ignoreCase = true) }
                        items(filtered) { poi ->
                            val nodeName = mapData.nodes.find { node -> node.id == poi.nodeId }?.name ?: "N/A"
                            ListRow(label = poi.name, onClick = { submitAndNavigate(nodeName) })
                        }
                    }
                    1 -> { // Professors
                        val filtered = profs.filter { p -> p.name.contains(query, ignoreCase = true) }
                        items(filtered) { prof ->
                            val roomName = mapData.nodes.find { node -> node.id == prof.officeNodeId }?.name ?: "N/A"
                            if (roomName != initialArrive) {
                                ListRow(
                                    label = "${prof.name} (${roomName.replace("Classe ", "")})",
                                    onClick = { submitAndNavigate(roomName) }
                                )
                            }
                        }
                    }
                    2 -> { // Rooms
                        val filtered = salles.filter { s -> s.name.replace("Classe ", "").contains(query, ignoreCase = true) }
                        items(filtered) { salle ->
                            ListRow(
                                label = salle.name.replace("Classe ", ""),
                                onClick = { submitAndNavigate(salle.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListRow(
    label: String,
    leading: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leading != null) {
            leading()
            Spacer(Modifier.width(12.dp))
        }
        Text(text = label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
    Divider()
}
