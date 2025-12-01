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
    val nodeId: String
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
            MapNode("ENTREE_OUEST", "Hall d'entrée Ouest", Offset(460f, 250f)),
            MapNode("ENTREE_NORDD_OUEST", "Entree residence", Offset(920f, 370f)),
            MapNode("ENTREE_NORDD_EST", "Entree Rio Tinto", Offset(920f, 720f)),
            MapNode("ENTREE_EST", "Entrée Est", Offset(460f, 830f)),
            MapNode("ACCEUIL", "ACCEUIL", Offset(468f, 760f)),
//
//            // Main Corridor Nodes
//            MapNode("C_OUEST_JONCTION", "Corridor", Offset(450f, 200f)),
//            MapNode("C_BLOC5_HAUT", "Corridor", Offset(350f, 200f)),
//            MapNode("C_BLOC5_OUEST", "Corridor", Offset(150f, 200f)),
//            MapNode("C_BLOC6_HAUT", "Corridor", Offset(350f, 400f)),
//            MapNode("C_BLOC6_BAS", "Corridor", Offset(350f, 650f)),
//            MapNode("C_BLOC7_BAS", "Corridor", Offset(450f, 750f)),
//            MapNode("C_BLOC7_OUEST", "Corridor", Offset(150f, 750f)),
//            MapNode("C_EST_JONCTION", "Corridor", Offset(600f, 750f)),
//            MapNode("C_CENTRAL", "Corridor", Offset(550f, 450f)),
//            MapNode("C_BLOC4_HAUT", "Corridor", Offset(550f, 250f)),
//            MapNode("C_BLOC3_HAUT", "Corridor", Offset(700f, 250f)),
//            MapNode("C_BLOC2_MILIEU", "Corridor", Offset(700f, 450f)),
//            MapNode("C_BLOC1_BAS", "Corridor", Offset(700f, 700f)),



            //escaliers
            MapNode("Escalier_p1_4_OUEST", "Escalier_p1_4_OUEST", Offset(490f, 380f), type = "Escalier"),
            MapNode("Escalier_p1_4_EST", "Escalier_p1_4_EST", Offset(490f, 712f), type = "Escalier"),
            //Ascenseur
            MapNode("Ascenseur_p1_4", "Ascenseur_p1_4", Offset(760f, 555f), type = "Ascenseur"),
//
//            // Classrooms (by BLOC)
            MapNode("P1_1020", "P1-1020", Offset(525f, 730f), type = "Classe"),
            MapNode("P1_1040", "P1-1040", Offset(580f, 730f), type = "Classe"),
            MapNode("P1_1045", "P1-1045", Offset(625f, 720f), type = "Classe"),
            MapNode("P1_1050", "P1-1050", Offset(698f, 720f), type = "Classe"),
            MapNode("P1_1080", "P1-1080", Offset(840f, 750f), type = "Classe"),


            MapNode("P1_2000", "P1-2000", Offset(845f, 705f), type = "Classe"),
            MapNode("P1_2010", "P1-2010", Offset(768f, 700f), type = "Classe"),
            MapNode("P1_2020", "P1-2020", Offset(765f, 641f), type = "Classe"),
            MapNode("P1_2030", "P1-2030", Offset(765f, 585f), type = "Classe"),
            MapNode("P1_2060", "P1-2060", Offset(770f, 572f), type = "Toillette Homme"),
            MapNode("P1_2070", "P1-2070", Offset(770f, 548f), type = "Toillette Femmes"),
            MapNode("P1_2080", "P1-2080", Offset(765f, 445f), type = "Classe"),
            MapNode("P1_2090", "P1-2090", Offset(765f, 413f), type = "Classe"),
            MapNode("P1_2110", "P1-2110", Offset(765f, 393f), type = "Classe"),


            MapNode("P1_3005", "P1-3005", Offset(813f, 370f), type = "Classe"),
            MapNode("P1_3010", "P1-3010", Offset(792f, 370f), type = "Classe"),
            MapNode("P1_3030", "P1-3030", Offset(556f, 370f), type = "Classe"),


            MapNode("P1_4020", "P1-4020", Offset(636f, 712f), type = "Classe"),
            MapNode("P1_4030", "P1-4030", Offset(698f, 712f), type = "Classe"),
            MapNode("P1_4035", "P1-4035", Offset(710f, 712f), type = "Classe"),
            MapNode("P1_4040", "P1-4040", Offset(760f, 700f), type = "Classe"),
            MapNode("P1_4050", "P1-4050", Offset(760f, 677f), type = "Classe"),
            MapNode("P1_4060", "P1-4060", Offset(760f, 665f), type = "Classe"),
            MapNode("P1_4070", "P1-4070", Offset(760f, 648f), type = "Classe"),
            MapNode("P1_4071", "P1-4071", Offset(716f, 620f), type = "Classe"),
            MapNode("P1_4072", "P1-4072", Offset(675f, 620f), type = "Classe"),
            MapNode("P1_4073", "P1-4073", Offset(656f, 612f), type = "Classe"),
            MapNode("P1_4075", "P1-4075", Offset(711f, 620f), type = "Classe"),
            MapNode("P1_4080", "P1-4080", Offset(725f, 620f), type = "Classe"),
            MapNode("P1_4090", "P1-4090", Offset(760f, 525f), type = "Classe"),
            MapNode("P1_4110", "P1-4110", Offset(718f, 510f), type = "Classe"),
            MapNode("P1_4112", "P1-4112", Offset(680f, 510f), type = "Classe"),
            MapNode("P1_4115", "P1-4115", Offset(658f, 510f), type = "Classe"),
            MapNode("P1_4120", "P1-4120", Offset(760f, 491f), type = "Classe"),
            MapNode("P1_4130", "P1-4130", Offset(760f, 478f), type = "Classe"),
            MapNode("P1_4140", "P1-4140", Offset(711f, 456f), type = "Classe"),
            MapNode("P1_4150", "P1-4150", Offset(708f, 456f), type = "Classe"),
            MapNode("P1_4160", "P1-4160", Offset(690f, 456f), type = "Classe"),
            MapNode("P1_4165", "P1-4165", Offset(682f, 456f), type = "Classe"),
            MapNode("P1_4170", "P1-4170", Offset(672f, 456f), type = "Classe"),
            MapNode("P1_4180", "P1-4180", Offset(760f, 445f), type = "Classe"),
            MapNode("P1_4190", "P1-4190", Offset(760f, 408f), type = "Classe"),
            MapNode("P1_4200", "P1-4200", Offset(760f, 393f), type = "Classe"),
            MapNode("P1_4205", "P1-4205", Offset(695f, 380f), type = "Classe"),
            MapNode("P1_4210", "P1-4210", Offset(680f, 400f), type = "Classe"),
            MapNode("P1_4220", "P1-4220", Offset(658f, 405f), type = "Classe"),
            MapNode("P1_4230", "P1-4230", Offset(645f, 405f), type = "Classe"),
            MapNode("P1_4240", "P1-4240", Offset(614f, 380f), type = "Classe"),
            MapNode("P1_4250", "P1-4250", Offset(559f, 380f), type = "Classe"),
            MapNode("P1_4260", "P1-4260", Offset(440f, 480f), type = "Local securité"),
            MapNode("P1_4270", "P1-4270", Offset(440f, 520f), type = "Classe"),
            MapNode("P1_4280", "P1-4280", Offset(440f, 585f), type = "Classe"),


            MapNode("P1_5000", "P1-5000", Offset(360f, 335f), type = "Classe"),
            MapNode("P1_5010", "P1-5010", Offset(305f, 335f), type = "Classe"),
            MapNode("P1_5020", "P1-5020", Offset(270f, 312f), type = "Classe"),
            MapNode("P1_5030", "P1-5030", Offset(290f, 273f), type = "Classe"),
            MapNode("P1_5040", "Sortie de secours OUEST", Offset(290f, 235f), type = "Secours"),
            MapNode("P1_5050", "P1-5050", Offset(222f, 295f), type = "Classe"),
            MapNode("P1_5060", "P1-5060", Offset(205f, 295f), type = "Classe"),
            MapNode("P1_5070", "P1-5070", Offset(135f, 295f), type = "Classe"),
            MapNode("P1_5080", "P1-5080", Offset(117f, 295f), type = "Classe"),
            MapNode("P1_5090", "P1-5090", Offset(117f, 327f), type = "Classe"),
            MapNode("P1_5100", "P1-5100", Offset(117f, 414f), type = "Classe"),
            MapNode("P1_5110", "P1-5110", Offset(117f, 426f), type = "Classe"),
            MapNode("P1_5120", "P1-5120", Offset(117f, 479f), type = "Classe"),
            MapNode("P1_5130", "P1-5130", Offset(117f, 532f), type = "Classe"),

//            MapNode("P1_6280", "P1-6280", Offset(175f, 700f), type = "Classe"),
//            MapNode("P1_6160", "P1-6160", Offset(175f, 610f), type = "Classe"),
//            MapNode("P1_6150", "P1-6150", Offset(175f, 520f), type = "Classe"),
//            MapNode("P1_6340", "P1-6340", Offset(175f, 430f), type = "Classe"),
//            MapNode("P1_6350", "P1-6350", Offset(175f, 340f), type = "Classe"),
//            MapNode("P1_6380", "P1-6380", Offset(175f, 250f), type = "Classe"),
//            MapNode("P1_6040", "P1-6040", Offset(225f, 250f), type = "Classe"),
//            MapNode("P1_6050", "P1-6050", Offset(225f, 285f), type = "Classe"),
//            MapNode("P1_6060", "P1-6060", Offset(225f, 315f), type = "Classe"),
//            MapNode("P1_6070", "P1-6070", Offset(225f, 350f), type = "Classe"),
//            MapNode("P1_6080", "P1-6080", Offset(350f, 325f), type = "Classe"),
//            MapNode("P1_6090", "P1-6090", Offset(350f, 405f), type = "Classe"),
//            MapNode("P1_6140", "P1-6140", Offset(350f, 485f), type = "Classe"),
//            MapNode("P1_6170", "P1-6170", Offset(350f, 565f), type = "Classe"),
//            MapNode("P1_6180", "P1-6180", Offset(350f, 650f), type = "Classe"),


            MapNode("P1_7000", "P1-7000", Offset(356f, 760f), type = "Classe"),
            MapNode("P1_7010", "P1-7010", Offset(292f, 760f), type = "Classe"),
            MapNode("P1_7050", "Sortie de secours EST", Offset(270f, 850f), type = "secours"),
            MapNode("P1_7060", "P1-7060", Offset(221f, 794f), type = "Classe"),
            MapNode("P1_7070", "P1-7070", Offset(167f, 794f), type = "Classe"),
            MapNode("P1_7070", "P1-7080", Offset(150f, 794f), type = "Classe"),
            MapNode("P1_7090", "P1-7090", Offset(122f, 794f), type = "Classe"),
            MapNode("P1_7100", "P1-7100", Offset(117f, 794f), type = "Local"),
            MapNode("P1_7110", "P1-7110", Offset(117f, 732f), type = "Classe"),
            MapNode("P1_7120", "P1-7120", Offset(117f, 680f), type = "Classe"),
            MapNode("P1_7130", "P1-7130", Offset(117f, 625f), type = "Classe"),
            MapNode("P1_7140", "P1-7140", Offset(117f, 613f), type = "Classe"),
        ),
        professors = listOf(
            Professor("Marie-Alix Autet", "P1_4080"),
            Professor("Johanne Truchon", "P1_4072"),
            Professor("Jessica Lapierre", "P1_2020"),
            Professor("Nathalia Matte", "P1_4050")
        ),
        poi = listOf(
            PointOfInterest("école de langue française et de culture quebecoise, centre du savoir sur mesure", "P1_2000"),
            PointOfInterest("Service des ressources financières", "P1_1080"),
            PointOfInterest("Bureau du registraire", "P1_1050"),
            PointOfInterest("Centre de référence le cube", "P1_4020"),
            PointOfInterest("Location de casier", "P1_1045")
        ),
        //refaire les edges
        //voir si on peut rajouter une valeur (correspond au temps)
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
            MapEdge("C_CENTRAL", "P1_4115"), MapEdge("P1_4115", "C_CENTral"),
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
            // BLOC 1
            Wall(Offset(630f, 720f), Offset(900f, 720f)),
            Wall(Offset(630f, 780f), Offset(900f, 780f)),
            Wall(Offset(630f, 720f), Offset(630f, 780f)),
            Wall(Offset(900f, 720f), Offset(900f, 780f)),

            // BLOC 2
            Wall(Offset(820f, 220f), Offset(900f, 220f)), //haut horizontal
            Wall(Offset(820f, 650f), Offset(900f, 650f)), //bas horizontal
            Wall(Offset(820f, 220f), Offset(820f, 650f)), //vertical gauche
            Wall(Offset(900f, 220f), Offset(900f, 650f)), //vertical droit
            // BLOC 3
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

            // BLOC 6
            Wall(Offset(150f, 230f), Offset(250f, 230f)),//haut horizontal
            Wall(Offset(250f, 230f), Offset(250f, 270f)),

            Wall(Offset(250f, 270f), Offset(380f, 270f)), //modif avec couloir
            Wall(Offset(380f, 270f), Offset(380f, 690f)), //vertical droit
            Wall(Offset(250f, 690f), Offset(380f, 690f)),
            Wall(Offset(250f, 690f), Offset(250f, 730f)),
            Wall(Offset(150f, 730f), Offset(250f, 730f)), //bas horizontal
            Wall(Offset(150f, 230f), Offset(150f, 730f)), //pas modif


        )
    )
}