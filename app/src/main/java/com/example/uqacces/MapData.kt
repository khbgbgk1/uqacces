package com.example.uqacces

import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Offset

data class MapNode(
    val id: String,
    val name: String,
    val position: Offset,
    val type: String = "",
    val waitDurationSeconds: Int = 0 //Pour estimer potentiellememt une attente
)

data class MapEdge(
    val startNodeId: String,
    val endNodeId: String,
    val weight: Float = 1.0f // en seconde
)

data class Wall(
    val start: Offset,
    val end: Offset
)

data class Professor(
    val name: String,
    val officeNodeId: String
)

data class PointOfInterest(
    val name: String,
    val officeNodeId: String
)

data class MapData(
    val nodes: List<MapNode>,
    val edges: List<MapEdge>,
    val walls: List<Wall>,
    val professors: List<Professor>,
    val poi :List<PointOfInterest>
)


data class MapBackground(
    @DrawableRes val resourceId: Int, // L'ID de la ressource (R.drawable.plan_1)
    val width: Float, // Largeur réelle de l'image si connue
    val height: Float // Hauteur réelle de l'image si connue
)

object UniversityMap {

    val background = MapBackground(
    resourceId = R.drawable.plan_1,
    // Ces valeurs (1000f, 1000f par exemple) sont CRUCIALES.
    // Elles définissent la taille du plan original par rapport à vos coordonnées (450f, 100f).
    width = 1000f,
    height = 1000f
    )
    val data = MapData(
        nodes = listOf(
            // Entrances & POIs
            MapNode("ENTREE_OUEST", "Hall d'entrée Ouest", Offset(450f, 100f)),
            MapNode("ENTREE_EST", "Entrée Est", Offset(600f, 900f)),
            MapNode("VESTIBULE_EST", "Vestibule Est", Offset(600f, 800f)),
            MapNode("TOILETTES_BLOC_2", "Toilettes Bloc 2", Offset(850f, 500f), type = "Toilettes"),
            MapNode("ASCENSEUR_CENTRAL", "Ascenseur", Offset(500f, 450f), type = "Ascenseur"),

            // Main Corridor Nodes
            MapNode("C_OUEST_JONCTION", "Corridor", Offset(450f, 200f)),
            MapNode("C_BLOC5_HAUT", "Corridor", Offset(350f, 200f)),
            MapNode("C_BLOC5_OUEST", "Corridor", Offset(150f, 200f)),
            MapNode("C_BLOC6_HAUT", "Corridor", Offset(350f, 400f)),
            MapNode("C_BLOC6_BAS", "Corridor", Offset(350f, 650f)),
            MapNode("C_BLOC7_BAS", "Corridor", Offset(450f, 750f)),
            MapNode("C_BLOC7_OUEST", "Corridor", Offset(150f, 750f)),
            MapNode("C_EST_JONCTION", "Corridor", Offset(600f, 750f)),
            MapNode("C_CENTRAL", "Corridor", Offset(550f, 450f)),
            MapNode("C_BLOC4_HAUT", "Corridor", Offset(550f, 250f)),
            MapNode("C_BLOC3_HAUT", "Corridor", Offset(700f, 250f)),
            MapNode("C_BLOC2_MILIEU", "Corridor", Offset(700f, 450f)),
            MapNode("C_BLOC1_BAS", "Corridor", Offset(700f, 700f)),

            // Classrooms (by BLOC)
            MapNode("P1_1050", "P1-1050", Offset(750f, 750f), type = "Classe"),
            MapNode("P1_1020", "P1-1020", Offset(650f, 750f), type = "Classe"),

            MapNode("P1_2030", "P1-2030", Offset(850f, 450f), type = "Classe"),
            MapNode("P1_2080", "P1-2080", Offset(850f, 350f), type = "Classe"),
            MapNode("P1_2190", "P1-2190", Offset(850f, 250f), type = "Classe"),
            MapNode("P1_3030", "P1-3030", Offset(500f, 150f), type = "Classe"),
            MapNode("P1_3010", "P1-3010", Offset(850f, 150f), type = "Classe"),

            MapNode("P1_4020", "P1-4020", Offset(575f, 600f), type = "Classe"),
            MapNode("P1_4280", "P1-4280", Offset(500f, 500f), type = "Classe"),
            MapNode("P1_4270", "P1-4270", Offset(500f, 450f), type = "Classe"),
            MapNode("P1_5015", "P1-5015", Offset(500f, 400f), type = "Classe"),
            MapNode("P1_4260", "P1-4260", Offset(500f, 350f), type = "Classe"),
            MapNode("P1_4250", "P1-4250", Offset(575f, 250f), type = "Classe"),
            MapNode("P1_4075", "P1-4075", Offset(650f, 500f), type = "Classe"),
            MapNode("P1_4115", "P1-4115", Offset(650f, 450f), type = "Classe"),
            MapNode("P1_4170", "P1-4170", Offset(650f, 375f), type = "Classe"),

            MapNode("P1_5130", "P1-5130", Offset(100f, 450f), type = "Classe"),
            MapNode("P1_5120", "P1-5120", Offset(100f, 400f), type = "Classe"),
            MapNode("P1_5110", "P1-5110", Offset(100f, 350f), type = "Classe"),
            MapNode("P1_5100", "P1-5100", Offset(100f, 300f), type = "Classe"),
            MapNode("P1_5090", "P1-5090", Offset(100f, 250f), type = "Classe"),
            MapNode("P1_5080", "P1-5080", Offset(100f, 200f), type = "Classe"),
            MapNode("P1_5070", "P1-5070", Offset(100f, 150f), type = "Classe"),
            MapNode("P1_5060", "P1-5060", Offset(150f, 150f), type = "Classe"),
            MapNode("P1_5050", "P1-5050", Offset(200f, 150f), type = "Classe"),
            MapNode("P1_5030", "P1-5030", Offset(250f, 150f), type = "Classe"),
            MapNode("P1_5010", "P1-5010", Offset(250f, 200f), type = "Classe"),
            MapNode("P1_5000", "P1-5000", Offset(350f, 200f), type = "Classe"),
            MapNode("P1_6280", "P1-6280", Offset(175f, 700f), type = "Classe"),
            MapNode("P1_6160", "P1-6160", Offset(175f, 610f), type = "Classe"),
            MapNode("P1_6150", "P1-6150", Offset(175f, 520f), type = "Classe"),
            MapNode("P1_6340", "P1-6340", Offset(175f, 430f), type = "Classe"),
            MapNode("P1_6350", "P1-6350", Offset(175f, 340f), type = "Classe"),
            MapNode("P1_6380", "P1-6380", Offset(175f, 250f), type = "Classe"),
            MapNode("P1_6040", "P1-6040", Offset(225f, 250f), type = "Classe"),
            MapNode("P1_6050", "P1-6050", Offset(225f, 285f), type = "Classe"),
            MapNode("P1_6060", "P1-6060", Offset(225f, 315f), type = "Classe"),
            MapNode("P1_6070", "P1-6070", Offset(225f, 350f), type = "Classe"),
            MapNode("P1_6080", "P1-6080", Offset(350f, 325f), type = "Classe"),
            MapNode("P1_6090", "P1-6090", Offset(350f, 405f), type = "Classe"),
            MapNode("P1_6140", "P1-6140", Offset(350f, 485f), type = "Classe"),
            MapNode("P1_6170", "P1-6170", Offset(350f, 565f), type = "Classe"),
            MapNode("P1_6180", "P1-6180", Offset(350f, 650f), type = "Classe"),
            MapNode("P1_7140", "P1-7140", Offset(100f, 500f), type = "Classe"),
            MapNode("P1_7130", "P1-7130", Offset(100f, 550f), type = "Classe"),
            MapNode("P1_7120", "P1-7120", Offset(100f, 625f), type = "Classe"),
            MapNode("P1_7110", "P1-7110", Offset(100f, 700f), type = "Classe"),
            MapNode("P1_7100", "P1-7100", Offset(100f, 775f), type = "Classe"),
            MapNode("P1_7090", "P1-7090", Offset(100f, 850f), type = "Classe"),
            MapNode("P1_7080", "P1-7080", Offset(150f, 850f), type = "Classe"),
            MapNode("P1_7070", "P1-7070", Offset(200f, 850f), type = "Classe"),
            MapNode("P1_7060", "P1-7060", Offset(250f, 850f), type = "Classe"),
            MapNode("P1_7010", "P1-7010", Offset(300f, 850f), type = "Classe"),
            MapNode("P1_7000", "P1-7000", Offset(350f, 850f), type = "Classe")
        ),
        //pas les bonnes classes, à voir avec les étages
        professors = listOf(
            Professor("Alice Leroi", "P1_6080"),
            Professor("Bob Martin", "P1_4075"),
            Professor("Claire Durand", "P1_2030"),
            Professor("David Gagnon", "P1_7120")
        ),
        edges = listOf(
            MapEdge("ASCENSEUR_CENTRAL", "C_CENTRAL"), MapEdge("C_CENTRAL", "ASCENSEUR_CENTRAL"),
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
            MapEdge("C_BLOC1_BAS", "P1_1050"), MapEdge("P1_1050", "C_BLOC1_BAS"),
            MapEdge("C_BLOC1_BAS", "P1_1020"), MapEdge("P1_1020", "C_BLOC1_BAS"),
            MapEdge("C_BLOC2_MILIEU", "P1_2030"), MapEdge("P1_2030", "C_BLOC2_MILIEU"),
            MapEdge("C_BLOC2_MILIEU", "P1_2080"), MapEdge("P1_2080", "C_BLOC2_MILIEU"),
            MapEdge("C_BLOC2_MILIEU", "P1_2190"), MapEdge("P1_2190", "C_BLOC2_MILIEU"),
            MapEdge("C_BLOC3_HAUT", "P1_3030"), MapEdge("P1_3030", "C_BLOC3_HAUT"),
            MapEdge("C_BLOC3_HAUT", "P1_3010"), MapEdge("P1_3010", "C_BLOC3_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4250"), MapEdge("P1_4250", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4260"), MapEdge("P1_4260", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_5015"), MapEdge("P1_5015", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4270"), MapEdge("P1_4270", "C_BLOC4_HAUT"),
            MapEdge("C_BLOC4_HAUT", "P1_4280"), MapEdge("P1_4280", "C_BLOC4_HAUT"),
            MapEdge("C_CENTRAL", "P1_4170"), MapEdge("P1_4170", "C_CENTRAL"),
            MapEdge("C_CENTRAL", "P1_4115"), MapEdge("P1_4115", "C_CENTRAL"),
            MapEdge("C_CENTRAL", "P1_4075"), MapEdge("P1_4075", "C_CENTRAL"),
            MapEdge("C_CENTRAL", "P1_4020"), MapEdge("P1_4020", "C_CENTRAL"),
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
        ),
        walls = listOf(
            // BLOC 1 fait
            Wall(Offset(630f, 720f), Offset(900f, 720f)),
            Wall(Offset(630f, 780f), Offset(900f, 780f)),
            Wall(Offset(630f, 720f), Offset(630f, 780f)),
            Wall(Offset(900f, 720f), Offset(900f, 780f)),

            // BLOC 2 fait
            Wall(Offset(820f, 220f), Offset(900f, 220f)), //haut horizontal
            Wall(Offset(820f, 650f), Offset(900f, 650f)), //bas horizontal
            Wall(Offset(820f, 220f), Offset(820f, 650f)), //vertical gauche
            Wall(Offset(900f, 220f), Offset(900f, 650f)), //vertical droit
            // BLOC 3 fait
            Wall(Offset(480f, 130f), Offset(900f, 130f)),
            Wall(Offset(480f, 180f), Offset(900f, 180f)),
            Wall(Offset(480f, 130f), Offset(480f, 180f)),
            Wall(Offset(900f, 130f), Offset(900f, 180f)),

            // BLOC 4
            //modif avec couloirs
            Wall(Offset(480f, 280f), Offset(680f, 280f)),
            Wall(Offset(480f, 670f), Offset(680f, 670f)),
            Wall(Offset(480f, 280f), Offset(480f, 670f)),
            Wall(Offset(680f, 280f), Offset(680f, 670f)),

            // BLOC 5 & 7
            Wall(Offset(80f, 110f), Offset(380f, 110f)),
            Wall(Offset(380f, 110f), Offset(380f, 240f)),
            Wall(Offset(270f, 240f), Offset(380f, 240f)),
            Wall(Offset(270f, 210f), Offset(270f, 240f)),
            Wall(Offset(120f, 210f), Offset(270f, 210f)),
            Wall(Offset(120f, 210f), Offset(120f, 750f)), // vertical intérieure
            Wall(Offset(120f, 750f), Offset(270f, 750f)),
            Wall(Offset(270f, 720f), Offset(270f, 750f)),
            Wall(Offset(270f, 720f), Offset(380f, 720f)),
            Wall(Offset(380f, 720f), Offset(380f, 870f)),
            Wall(Offset(80f, 870f), Offset(380f, 870f)),
            Wall(Offset(80f, 110f), Offset(80f, 870f)),

            // BLOC 6 fait
            Wall(Offset(150f, 230f), Offset(250f, 230f)),//haut horizontal
            Wall(Offset(250f, 230f), Offset(250f, 270f)),
            Wall(Offset(250f, 270f), Offset(380f, 270f)), //modif avec couloir
            Wall(Offset(380f, 270f), Offset(380f, 690f)), //vertical droit
            Wall(Offset(250f, 690f), Offset(380f, 690f)),
            Wall(Offset(250f, 690f), Offset(250f, 730f)),
            Wall(Offset(150f, 730f), Offset(250f, 730f)), //bas horizontal
            Wall(Offset(150f, 230f), Offset(150f, 730f)), //pas modif


        ),
        poi = listOf(),
    )
}
