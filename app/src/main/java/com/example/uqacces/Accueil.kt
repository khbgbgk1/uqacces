package com.example.uqacces

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/*
* Ecran d'accueil avec le plan et barre de recherche (Arrivée).
*L'utilisateur peut zoomer/déplacer le plan
* */

@Composable
fun Accueil(
    onSearchClick: () -> Unit,
    startNodeName: String? = null,
    endNodeName: String? = null
) {
    // Contenu du champ (affiche la destination choisie si on revient plus tard)
    var arrivee by remember { mutableStateOf(TextFieldValue("")) }

    // Map data based on the provided university plan image.
    val universityMapData = remember {
        val nodes = listOf(
            // Entrances
            MapNode("ENTREE_OUEST", "Hall d'entrée Ouest", Offset(450f, 100f)),
            MapNode("ENTREE_EST", "Entrée Est", Offset(600f, 900f)),
            MapNode("VESTIBULE_EST", "Vestibule Est", Offset(600f, 800f)),


            // Main Corridor Nodes (junctions and turns)
            MapNode("C_OUEST_JONCTION", "Corridor", Offset(450f, 200f)), // Near Ouest
            MapNode("C_BLOC5_HAUT", "Corridor", Offset(350f, 200f)),
            MapNode("C_BLOC5_OUEST", "Corridor", Offset(150f, 200f)),
            MapNode("C_BLOC6_HAUT", "Corridor", Offset(350f, 400f)),
            MapNode("C_BLOC6_BAS", "Corridor", Offset(350f, 650f)),
            MapNode("C_BLOC7_BAS", "Corridor", Offset(450f, 750f)),
            MapNode("C_BLOC7_OUEST", "Corridor", Offset(150f, 750f)),
            MapNode("C_EST_JONCTION", "Corridor", Offset(600f, 750f)), // Near Est
            MapNode("C_CENTRAL", "Corridor", Offset(550f, 450f)),
            MapNode("C_BLOC4_HAUT", "Corridor", Offset(550f, 250f)),
            MapNode("C_BLOC3_HAUT", "Corridor", Offset(700f, 250f)),
            MapNode("C_BLOC2_MILIEU", "Corridor", Offset(700f, 450f)),
            MapNode("C_BLOC1_BAS", "Corridor", Offset(700f, 700f)),

            // BLOC 1
            MapNode("P1_1050", "Classe P1-1050", Offset(750f, 750f)),
            MapNode("P1_1020", "Classe P1-1020", Offset(650f, 750f)),

            // BLOC 2
            MapNode("P1_2030", "Classe P1-2030", Offset(850f, 450f)),
            MapNode("P1_2080", "Classe P1-2080", Offset(850f, 350f)),
            MapNode("P1_2190", "Classe P1-2190", Offset(850f, 250f)),

            // BLOC 3
            MapNode("P1_3030", "Classe P1-3030", Offset(500f, 150f)),
            MapNode("P1_3010", "Classe P1-3010", Offset(850f, 150f)),

            //BLOC 4
            MapNode("P1_4020", "Classe P1-4020", Offset(500f, 600f)),
            MapNode("P1_4280", "Classe P1-4280", Offset(450f, 500f)),
            MapNode("P1_4270", "Classe P1-4270", Offset(450f, 450f)),
            MapNode("P1_5015", "Classe P1-5015", Offset(450f, 400f)),
            MapNode("P1_4260", "Classe P1-4260", Offset(450f, 350f)),
            MapNode("P1_4250", "Classe P1-4250", Offset(500f, 300f)),
            MapNode("P1_4075", "Classe P1-4075", Offset(550f, 500f)),
            MapNode("P1_4115", "Classe P1-4115", Offset(550f, 450f)),
            MapNode("P1_4170", "Classe P1-4170", Offset(550f, 375f)),

            //BLOC 5
            MapNode("P1_5130", "Classe P1-5130", Offset(100f, 450f)),
            MapNode("P1_5120", "Classe P1-5120", Offset(100f, 400f)),
            MapNode("P1_5110", "Classe P1-5110", Offset(100f, 350f)),
            MapNode("P1_5100", "Classe P1-5100", Offset(100f, 300f)),
            MapNode("P1_5090", "Classe P1-5090", Offset(100f, 250f)),
            MapNode("P1_5080", "Classe P1-5080", Offset(100f, 200f)),
            MapNode("P1_5070", "Classe P1-5070", Offset(100f, 150f)),
            MapNode("P1_5060", "Classe P1-5060", Offset(150f, 150f)),
            MapNode("P1_5050", "Classe P1-5050", Offset(200f, 150f)),
            MapNode("P1_5030", "Classe P1-5030", Offset(250f, 150f)),
            MapNode("P1_5010", "Classe P1-5010", Offset(250f, 200f)),
            MapNode("P1_5000", "Classe P1-5000", Offset(350f, 200f)),

            //BLOC 6
            MapNode("P1_6280", "Classe P1-6280", Offset(175f, 700f)),
            MapNode("P1_6160", "Classe P1-6160", Offset(175f, 610f)),
            MapNode("P1_6150", "Classe P1-6150", Offset(175f, 520f)),
            MapNode("P1_6340", "Classe P1-6340", Offset(175f, 430f)),
            MapNode("P1_6350", "Classe P1-6350", Offset(175f, 340f)),
            MapNode("P1_6380", "Classe P1-6380", Offset(175f, 250f)),
            MapNode("P1_6040", "Classe P1-6040", Offset(225f, 250f)),
            MapNode("P1_6050", "Classe P1-6050", Offset(225f, 285f)),
            MapNode("P1_6060", "Classe P1-6060", Offset(225f, 315f)),
            MapNode("P1_6070", "Classe P1-6070", Offset(225f, 350f)),
            MapNode("P1_6080", "Classe P1-6080", Offset(350f, 325f)),
            MapNode("P1_6090", "Classe P1-6090", Offset(350f, 405f)),
            MapNode("P1_6140", "Classe P1-6140", Offset(350f, 485f)),
            MapNode("P1_6170", "Classe P1-6170", Offset(350f, 565f)),
            MapNode("P1_6180", "Classe P1-6180", Offset(350f, 650f)),

            //BLOC 7
            MapNode("P1_7140", "Classe P1-7140", Offset(100f, 500f)),
            MapNode("P1_7130", "Classe P1-7130", Offset(100f, 550f)),
            MapNode("P1_7120", "Classe P1-7120", Offset(100f, 625f)),
            MapNode("P1_7110", "Classe P1-7110", Offset(100f, 700f)),
            MapNode("P1_7100", "Classe P1-7100", Offset(100f, 775f)),
            MapNode("P1_7090", "Classe P1-7090", Offset(100f, 850f)),
            MapNode("P1_7080", "Classe P1-7080", Offset(150f, 850f)),
            MapNode("P1_7070", "Classe P1-7070", Offset(200f, 850f)),
            MapNode("P1_7060", "Classe P1-7060", Offset(250f, 850f)),
            MapNode("P1_7010", "Classe P1-7010", Offset(300f, 850f)),
            MapNode("P1_7000", "Classe P1-7000", Offset(350f, 850f))
        )

        val edges = listOf(
            // Main corridor graph
            MapEdge("ENTREE_OUEST", "C_OUEST_JONCTION"), MapEdge("C_OUEST_JONCTION", "ENTREE_OUEST"),
            MapEdge("C_OUEST_JONCTION", "C_BLOC5_HAUT"), MapEdge("C_BLOC5_HAUT", "C_OUEST_JONCTION"),
            MapEdge("C_BLOC5_HAUT", "C_BLOC5_OUEST"), MapEdge("C_BLOC5_OUEST", "C_BLOC5_HAUT"),
            MapEdge("C_BLOC5_HAUT", "C_BLOC6_HAUT"), MapEdge("C_BLOC6_HAUT", "C_BLOC5_HAUT"),
            MapEdge("C_BLOC6_HAUT", "C_BLOC6_BAS"), MapEdge("C_BLOC6_BAS", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_BAS", "C_BLOC7_BAS"), MapEdge("C_BLOC7_BAS", "C_BLOC6_BAS"),
            MapEdge("C_BLOC7_BAS", "C_BLOC7_OUEST"), MapEdge("C_BLOC7_OUEST", "C_BLOC7_BAS"),
            MapEdge("C_BLOC7_BAS", "C_EST_JONCTION"), MapEdge("C_EST_JONCTION", "C_BLOC7_BAS"),
            MapEdge("C_EST_JONCTION", "VESTIBULE_EST"), MapEdge("VESTIBULE_EST", "C_EST_JONCTION"),
            MapEdge("VESTIBULE_EST", "ENTREE_EST"), MapEdge("ENTREE_EST", "VESTIBULE_EST"),
            MapEdge("C_OUEST_JONCTION", "C_BLOC4_HAUT"), MapEdge("C_BLOC4_HAUT", "C_OUEST_JONCTION"),
            MapEdge("C_BLOC4_HAUT", "C_CENTRAL"), MapEdge("C_CENTRAL", "C_BLOC4_HAUT"),
            MapEdge("C_CENTRAL", "C_EST_JONCTION"), MapEdge("C_EST_JONCTION", "C_CENTRAL"),
            MapEdge("C_BLOC4_HAUT", "C_BLOC3_HAUT"), MapEdge("C_BLOC3_HAUT", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC3_HAUT", "C_BLOC2_MILIEU"), MapEdge("C_BLOC2_MILIEU", "C_BLOC3_HAUT"),
            MapEdge("C_BLOC2_MILIEU", "C_BLOC1_BAS"), MapEdge("C_BLOC1_BAS", "C_BLOC2_MILIEU"),
            MapEdge("C_BLOC1_BAS", "C_EST_JONCTION"), MapEdge("C_EST_JONCTION", "C_BLOC1_BAS"),

            // BLOC 1 Connections
            MapEdge("C_BLOC1_BAS", "P1_1050"), MapEdge("P1_1050", "C_BLOC1_BAS"),
            MapEdge("C_BLOC1_BAS", "P1_1020"), MapEdge("P1_1020", "C_BLOC1_BAS"),

            // BLOC 2 Connections
            MapEdge("C_BLOC2_MILIEU", "P1_2030"), MapEdge("P1_2030", "C_BLOC2_MILIEU"),
            MapEdge("C_BLOC2_MILIEU", "P1_2080"), MapEdge("P1_2080", "C_BLOC2_MILIEU"),
            MapEdge("C_BLOC2_MILIEU", "P1_2190"), MapEdge("P1_2190", "C_BLOC2_MILIEU"),

            // BLOC 3 Connections
            MapEdge("C_BLOC3_HAUT", "P1_3030"), MapEdge("P1_3030", "C_BLOC3_HAUT"),
            MapEdge("C_BLOC3_HAUT", "P1_3010"), MapEdge("P1_3010", "C_BLOC3_HAUT"),

            // BLOC 4 Connections
            MapEdge("C_BLOC4_HAUT", "P1_4250"), MapEdge("P1_4250", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4260"), MapEdge("P1_4260", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_5015"), MapEdge("P1_5015", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4270"), MapEdge("P1_4270", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4280"), MapEdge("P1_4280", "C_BLOC4_HAUT"),
            MapEdge("C_CENTRAL", "P1_4170"), MapEdge("P1_4170", "C_CENTRAL"),
            MapEdge("C_CENTRAL", "P1_4115"), MapEdge("P1_4115", "C_CENTRAL"),
            MapEdge("C_CENTRAL", "P1_4075"), MapEdge("P1_4075", "C_CENTRAL"),
            MapEdge("C_CENTRAL", "P1_4020"), MapEdge("P1_4020", "C_CENTRAL"),

            // BLOC 5 Connections
            MapEdge("C_BLOC5_OUEST", "P1_5070"), MapEdge("P1_5070", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_OUEST", "P1_5080"), MapEdge("P1_5080", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_OUEST", "P1_5090"), MapEdge("P1_5090", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_OUEST", "P1_5100"), MapEdge("P1_5100", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_OUEST", "P1_5110"), MapEdge("P1_5110", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_OUEST", "P1_5120"), MapEdge("P1_5120", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_OUEST", "P1_5130"), MapEdge("P1_5130", "C_BLOC5_OUEST"),
            MapEdge("C_BLOC5_HAUT", "P1_5060"), MapEdge("P1_5060", "C_BLOC5_HAUT"),
            MapEdge("C_BLOC5_HAUT", "P1_5050"), MapEdge("P1_5050", "C_BLOC5_HAUT"),
            MapEdge("C_BLOC5_HAUT", "P1_5030"), MapEdge("P1_5030", "C_BLOC5_HAUT"),
            MapEdge("C_BLOC5_HAUT", "P1_5010"), MapEdge("P1_5010", "C_BLOC5_HAUT"),
            MapEdge("C_BLOC5_HAUT", "P1_5000"), MapEdge("P1_5000", "C_BLOC5_HAUT"),

            // BLOC 6 Connections
            MapEdge("C_BLOC6_HAUT", "P1_6380"), MapEdge("P1_6380", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6350"), MapEdge("P1_6350", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6040"), MapEdge("P1_6040", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6050"), MapEdge("P1_6050", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6060"), MapEdge("P1_6060", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6070"), MapEdge("P1_6070", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6080"), MapEdge("P1_6080", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_HAUT", "P1_6090"), MapEdge("P1_6090", "C_BLOC6_HAUT"),
            MapEdge("C_BLOC6_BAS", "P1_6340"), MapEdge("P1_6340", "C_BLOC6_BAS"),
            MapEdge("C_BLOC6_BAS", "P1_6150"), MapEdge("P1_6150", "C_BLOC6_BAS"),
            MapEdge("C_BLOC6_BAS", "P1_6160"), MapEdge("P1_6160", "C_BLOC6_BAS"),
            MapEdge("C_BLOC6_BAS", "P1_6280"), MapEdge("P1_6280", "C_BLOC6_BAS"),
            MapEdge("C_BLOC6_BAS", "P1_6140"), MapEdge("P1_6140", "C_BLOC6_BAS"),
            MapEdge("C_BLOC6_BAS", "P1_6170"), MapEdge("P1_6170", "C_BLOC6_BAS"),
            MapEdge("C_BLOC6_BAS", "P1_6180"), MapEdge("P1_6180", "C_BLOC6_BAS"),

            // BLOC 7 Connections
            MapEdge("C_BLOC7_OUEST", "P1_7140"), MapEdge("P1_7140", "C_BLOC7_OUEST"),
            MapEdge("C_BLOC7_OUEST", "P1_7130"), MapEdge("P1_7130", "C_BLOC7_OUEST"),
            MapEdge("C_BLOC7_OUEST", "P1_7120"), MapEdge("P1_7120", "C_BLOC7_OUEST"),
            MapEdge("C_BLOC7_OUEST", "P1_7110"), MapEdge("P1_7110", "C_BLOC7_OUEST"),
            MapEdge("C_BLOC7_OUEST", "P1_7100"), MapEdge("P1_7100", "C_BLOC7_OUEST"),
            MapEdge("C_BLOC7_OUEST", "P1_7090"), MapEdge("P1_7090", "C_BLOC7_OUEST"),
            MapEdge("C_BLOC7_BAS", "P1_7080"), MapEdge("P1_7080", "C_BLOC7_BAS"),
            MapEdge("C_BLOC7_BAS", "P1_7070"), MapEdge("P1_7070", "C_BLOC7_BAS"),
            MapEdge("C_BLOC7_BAS", "P1_7060"), MapEdge("P1_7060", "C_BLOC7_BAS"),
            MapEdge("C_BLOC7_BAS", "P1_7010"), MapEdge("P1_7010", "C_BLOC7_BAS"),
            MapEdge("C_BLOC7_BAS", "P1_7000"), MapEdge("P1_7000", "C_BLOC7_BAS")
        )

        val walls = listOf(
            // Corridor walls based on the map layout
            Wall(Offset(80f, 130f), Offset(80f, 900f)),      // Far-left vertical
            Wall(Offset(120f, 130f), Offset(120f, 480f)),    // Inner-left vertical (top part)
            Wall(Offset(120f, 480f), Offset(200f, 480f)),    // ---
            Wall(Offset(200f, 480f), Offset(200f, 800f)),
            Wall(Offset(200f, 800f), Offset(400f, 800f)),
            Wall(Offset(400f, 800f), Offset(400f, 880f)),
            Wall(Offset(80f, 900f), Offset(400f, 900f)),     // Bottom horizontal

            Wall(Offset(120f, 130f), Offset(430f, 130f)),    // Top horizontal
            Wall(Offset(430f, 130f), Offset(430f, 180f)),
            Wall(Offset(430f, 180f), Offset(470f, 180f)),
            Wall(Offset(470f, 130f), Offset(920f, 130f)),
            Wall(Offset(920f, 130f), Offset(920f, 230f)),
            Wall(Offset(940f, 230f), Offset(940f, 520f)),
            Wall(Offset(920f, 520f), Offset(920f, 880f)),
            Wall(Offset(920f, 880f), Offset(650f, 880f)),
            Wall(Offset(650f, 880f), Offset(650f, 820f)),
            Wall(Offset(400f, 880f), Offset(580f, 880f)),

            // Internal Corridors
            Wall(Offset(330f, 180f), Offset(330f, 830f)),
            Wall(Offset(370f, 180f), Offset(370f, 830f)),

            Wall(Offset(370f, 230f), Offset(680f, 230f)),
            Wall(Offset(370f, 270f), Offset(680f, 270f)),

            Wall(Offset(530f, 270f), Offset(530f, 730f)),
            Wall(Offset(570f, 270f), Offset(570f, 730f)),

            Wall(Offset(570f, 430f), Offset(680f, 430f)),
            Wall(Offset(570f, 470f), Offset(680f, 470f)),

            Wall(Offset(680f, 270f), Offset(680f, 730f)),
            Wall(Offset(720f, 270f), Offset(720f, 730f))
        )
        MapData(nodes, edges, walls)
    }

    // Find the shortest path if start and end node names are provided
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
        // Example path if none provided, e.g., from Entrance Ouest to a room.
        remember(universityMapData) {
            findShortestPath(universityMapData, "ENTREE_OUEST", "P1_4270")
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
            // MapView with path
            MapView(
                mapData = universityMapData,
                pathNodeIds = path,
                modifier = Modifier.fillMaxSize()
            )

            // Champ "Arrivée" cliquable -> ouvre l’écran Recherche
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
