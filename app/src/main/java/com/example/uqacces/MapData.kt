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
    val data: MapData
        get() = MapData(
            nodes = listOf(
                // Entrances & POIs
                MapNode("ENTREE_OUEST", "Hall d'entrée Ouest", Offset(460f, 250f)),
                MapNode("ENTREE_NORD_OUEST", "Entree residence", Offset(920f, 370f)),
                MapNode("ENTREE_NORD_EST", "Entree Rio Tinto", Offset(920f, 720f)),
                MapNode("ENTREE_EST", "Entrée Est", Offset(460f, 830f)),
                MapNode("ACCUEIL", "ACCUEIL", Offset(468f, 760f)),

                // Main Corridor Nodes (not visible)
                MapNode("C_EST_Centre_Accueil", "Corridor", Offset(445f, 712f),type = "Corridor"),
                MapNode("C_EST_Centre_Escalier", "Corridor", Offset(445f, 680f),type = "Corridor"),
                MapNode("C_OUEST_Centre_Escalier", "Corridor", Offset(435f, 420f),type = "Corridor"),
                MapNode("C_OUEST_Centre_Entree", "Corridor", Offset(460f, 335f),type = "Corridor"),

                MapNode("C_OUEST_1060", "Corridor", Offset(762f, 720f),type = "Corridor"),
                MapNode("C_OUEST_1080", "Corridor", Offset(840f, 720f),type = "Corridor"),

                MapNode("C_OUEST_3010", "Corridor", Offset(762f, 370f),type = "Corridor"),

                MapNode("C_OUEST_4080", "Corridor", Offset(762f, 620f),type = "Corridor"),
                MapNode("C_EST_4120", "Corridor", Offset(762f, 510f),type = "Corridor"),
                MapNode("C_EST_4180", "Corridor", Offset(762f, 458f),type = "Corridor"),
                MapNode("C_OUEST_4205", "Corridor", Offset(680f, 380f),type = "Corridor"),

                MapNode("C_OUEST_5050", "Corridor", Offset(290f, 295f),type = "Corridor"),
                MapNode("C_OUEST_5060", "Corridor", Offset(265f, 295f),type = "Corridor"),
                MapNode("C_OUEST_5090", "Corridor", Offset(117f, 375f),type = "Corridor"),

                MapNode("C_EST_6_Toilette", "Corridor", Offset(370f, 680f),type = "Corridor"),
                MapNode("C_OUEST_6_Toilette", "Corridor", Offset(370f, 420f),type = "Corridor"),
                MapNode("C_OUEST_6180", "Corridor", Offset(265f, 650f),type = "Corridor"),


                MapNode("C_OUEST_7050", "Corridor", Offset(265f, 794f),type = "Corridor"),
                MapNode("C_OUEST_7010", "Corridor", Offset(265f, 760f),type = "Corridor"),



                //escaliers
                MapNode("Escalier_p1_4_OUEST", "Escalier_p1_4_OUEST", Offset(490f, 380f), type = "Escalier"),
                MapNode("Escalier_p1_4_EST", "Escalier_p1_4_EST", Offset(490f, 712f), type = "Escalier"),
                MapNode("Escalier_p1_6_OUEST", "Escalier_p1_6_OUEST", Offset(410f, 445f), type = "Escalier"),
                MapNode("Escalier_p1_6_EST", "Escalier_p1_6_EST", Offset(410f, 650f), type = "Escalier"),
                MapNode("Escalier_Secours_p1_6_OUEST", "Escalier_Secours_p1_6_OUEST", Offset(135f, 365f), type = "Secours Escalier"),
                MapNode("Escalier_Secours_p1_6_EST", "Escalier_Secours_p1_6_EST", Offset(135f, 732f), type = "Secours Escalier"),
                //Ascenseur
                MapNode("Ascenseur_p1_4", "Ascenseur_p1_4", Offset(760f, 555f), type = "Ascenseur"),
                MapNode("Ascenseur_p1_6_OUEST", "Ascenseur_p1_6_OUEST", Offset(380f, 379f), type = "Ascenseur"),
                MapNode("Ascenseur_p1_6_EST", "Ascenseur_p1_6_EST", Offset(380f, 717f), type = "Ascenseur"),
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
                MapNode("P1_2060", "P1-2060", Offset(770f, 572f), type = "Toilette Homme"),
                MapNode("P1_2070", "P1-2070", Offset(770f, 548f), type = "Toilette Femmes"),
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


                MapNode("Toilette_P1_6_OUEST", "Toilette_P1_6_OUEST", Offset(360f, 358f), type = "Toilette Mixte"),
                MapNode("Toilette_P1_6_EST", "Toilette_P1_6_EST", Offset(360f, 735f), type = "Toilette Mixte"),
                MapNode("P1_6040", "P1-6040", Offset(258f, 312f), type = "Classe"),
                MapNode("P1_6050", "P1-6050", Offset(258f, 338f), type = "Classe"),
                MapNode("P1_6060", "P1-6060", Offset(258f, 375f), type = "Classe"),
                MapNode("P1_6070", "P1-6070", Offset(258f, 412f), type = "Classe"),
                MapNode("P1_6080", "P1-6080", Offset(265f, 430f), type = "Classe"),
                MapNode("P1_6090", "P1-6090", Offset(339f, 445f), type = "Classe"),
                MapNode("P1_6120", "P1-6120", Offset(258f, 451f), type = "Classe"),
                MapNode("P1_6130", "P1-6130", Offset(258f, 486f), type = "Classe"),
                MapNode("P1_6135", "P1-6135", Offset(258f, 508f), type = "Classe"),
                MapNode("P1_6140", "P1-6140", Offset(265f, 520f), type = "Classe"),
                MapNode("P1_6150", "P1-6150", Offset(258f, 573f), type = "Classe"),
                MapNode("P1_6160", "P1-6160", Offset(258f, 606f), type = "Classe"),
                MapNode("P1_6170", "P1-6170", Offset(339f, 650f), type = "Classe"),
                MapNode("P1_6180", "P1-6180", Offset(265f, 668f), type = "Classe"),
                MapNode("P1_6230", "P1-6230", Offset(221f, 790f), type = "Classe"),
                MapNode("P1_6290", "P1-6290", Offset(140f, 717f), type = "Classe"),
                MapNode("P1_6310", "P1-6310", Offset(135f, 717f), type = "Classe"),
                MapNode("P1_6320", "P1-6320", Offset(125f, 700f), type = "Classe"),
                MapNode("P1_6330", "P1-6330", Offset(125f, 640f), type = "Classe"),
                MapNode("P1_6340", "P1-6340", Offset(125f, 458f), type = "Classe"),
                MapNode("P1_6350", "P1-6350", Offset(125f, 395f), type = "Classe"),
                MapNode("P1_6360", "P1-6360", Offset(135f, 383f), type = "Classe"),
                MapNode("P1_6370", "P1-6370", Offset(142f, 381f), type = "Classe"),
                MapNode("P1_6380", "P1-6380", Offset(156f, 300f), type = "Classe"),


                MapNode("P1_7000", "P1-7000", Offset(356f, 760f), type = "Classe"),
                MapNode("P1_7010", "P1-7010", Offset(292f, 760f), type = "Classe"),
                MapNode("P1_7050", "Sortie de secours EST", Offset(270f, 850f), type = "secours"),
                MapNode("P1_7060", "P1-7060", Offset(221f, 796f), type = "Classe"),
                MapNode("P1_7070", "P1-7070", Offset(167f, 794f), type = "Classe"),
                MapNode("P1_7080", "P1-7080", Offset(150f, 794f), type = "Classe"),
                MapNode("P1_7090", "P1-7090", Offset(122f, 794f), type = "Classe"),
                MapNode("P1_7100", "P1-7100", Offset(117f, 794f), type = "Local"),
                MapNode("P1_7110", "P1-7110", Offset(117f, 732f), type = "Classe"),
                MapNode("P1_7120", "P1-7120", Offset(117f, 680f), type = "Classe"),
                MapNode("P1_7130", "P1-7130", Offset(117f, 625f), type = "Classe"),
                MapNode("P1_7140", "P1-7140", Offset(117f, 613f), type = "Classe"),
            ),
            //pas les bonnes classes, à voir avec les étages
            professors = listOf(
                Professor("Marie-Alix Autet (P1_4080)", "P1_4080"),
                Professor("Johanne Truchon (P1_4072)", "P1_4072"),
                Professor("Jessica Lapierre (P1_2020)", "P1_2020"),
                Professor("Nathalia Matte (P1_4050)", "P1_4050")
            ),



            edges = listOf(
                //MapEdge("P1_7120", "P1_7130",5f),MapEdge("P1_7130", "P1_7120",5f),
                //MapEdge("P1_7130", "P1_7140",2f),MapEdge("P1_7140", "P1_7130",2f),

                // Main Corridor Nodes (East-West & North-South spine)
                MapEdge("ENTREE_OUEST", "C_OUEST_Centre_Entree", 4f),
                MapEdge("C_OUEST_Centre_Entree", "ENTREE_OUEST", 4f),

                MapEdge("ENTREE_EST", "ACCUEIL", 1f),
                MapEdge("ACCUEIL", "ENTREE_EST", 1f),

                MapEdge("ACCUEIL", "C_EST_Centre_Accueil", 1f),
                MapEdge("C_EST_Centre_Accueil", "ACCUEIL", 1f),

                MapEdge("C_EST_Centre_Accueil", "C_EST_Centre_Escalier", 1f),
                MapEdge("C_EST_Centre_Escalier", "C_EST_Centre_Accueil", 1f),

                MapEdge("C_EST_Centre_Accueil", "Escalier_p1_4_EST", 1f),
                MapEdge("Escalier_p1_4_EST", "C_EST_Centre_Accueil", 1f),

                MapEdge("C_OUEST_Centre_Entree", "Escalier_p1_4_OUEST", 1f),
                MapEdge("Escalier_p1_4_OUEST", "C_OUEST_Centre_Entree", 1f),

                MapEdge("C_OUEST_Centre_Escalier", "C_OUEST_Centre_Entree", 1f),
                MapEdge("C_OUEST_Centre_Entree", "C_OUEST_Centre_Escalier", 1f),

                MapEdge("C_OUEST_Centre_Escalier", "Escalier_p1_6_OUEST", 1f),
                MapEdge("Escalier_p1_6_OUEST", "C_OUEST_Centre_Escalier", 1f),

                MapEdge("C_EST_Centre_Escalier", "Escalier_p1_6_EST", 1f),
                MapEdge("Escalier_p1_6_EST", "C_EST_Centre_Escalier", 1f),

                MapEdge("C_OUEST_1060", "C_OUEST_1080", 1f),
                MapEdge("C_OUEST_1080", "C_OUEST_1060", 1f),

                MapEdge("ENTREE_NORD_EST", "C_OUEST_1080", 1f),
                MapEdge("C_OUEST_1080", "ENTREE_NORD_EST", 1f),

                MapEdge("ENTREE_NORD_OUEST", "P1_3005", 1f),
                MapEdge("P1_3005", "ENTREE_NORD_OUEST", 1f),

                MapEdge("C_OUEST_Centre_Escalier", "C_OUEST_6_Toilette", 1f),
                MapEdge("C_OUEST_6_Toilette", "C_OUEST_Centre_Escalier", 1f),

                MapEdge("C_OUEST_6_Toilette", "Ascenseur_p1_6_OUEST", 1f),
                MapEdge("Ascenseur_p1_6_OUEST", "C_OUEST_6_Toilette", 1f),

                MapEdge("C_OUEST_6_Toilette", "Escalier_p1_6_OUEST", 1f),
                MapEdge("Escalier_p1_6_OUEST", "C_OUEST_6_Toilette", 1f),

                MapEdge("C_EST_Centre_Escalier", "C_EST_6_Toilette", 1f),
                MapEdge("C_EST_6_Toilette", "C_EST_Centre_Escalier", 1f),

                MapEdge("C_EST_6_Toilette", "Ascenseur_p1_6_EST", 1f),
                MapEdge("Ascenseur_p1_6_EST", "C_EST_6_Toilette", 1f),

                MapEdge("C_EST_6_Toilette", "Escalier_p1_6_EST", 1f),
                MapEdge("Escalier_p1_6_EST", "C_EST_6_Toilette", 1f),

                MapEdge("Toilette_P1_6_EST", "C_EST_6_Toilette", 1f),
                MapEdge("C_EST_6_Toilette", "Toilette_P1_6_EST", 1f),

                MapEdge("Toilette_P1_6_OUEST", "C_OUEST_6_Toilette", 1f),
                MapEdge("C_OUEST_6_Toilette", "Toilette_P1_6_OUEST", 1f),

                MapEdge("C_OUEST_7010", "C_OUEST_7050", 1f),
                MapEdge("C_OUEST_7050", "C_OUEST_7010", 1f),


                // Couloir Nord-Est
                MapEdge("P1_1020", "Escalier_p1_4_EST", 1f), MapEdge("Escalier_p1_4_EST", "P1_1020", 1f),
                MapEdge("P1_4020", "Escalier_p1_4_EST", 1f), MapEdge("Escalier_p1_4_EST", "P1_4020", 1f),
                MapEdge("P1_1020", "P1_1040", 1f), MapEdge("P1_1040", "P1_1020", 1f),
                MapEdge("P1_1040", "P1_1045", 1f), MapEdge("P1_1045", "P1_1040", 1f),
                MapEdge("P1_1045", "P1_4020", 1f), MapEdge("P1_4020", "P1_1045", 1f),
                MapEdge("P1_1045", "P1_1050", 1f), MapEdge("P1_1050", "P1_1045", 1f),
                MapEdge("P1_1050", "C_OUEST_1060", 1f), MapEdge("C_OUEST_1060", "P1_1050", 1f),
                MapEdge("P1_1050", "P1_4030", 1f), MapEdge("P1_4030", "P1_1050", 1f),
                MapEdge("P1_1050", "P1_4035", 1f), MapEdge("P1_4035", "P1_1050", 1f),
                MapEdge("P1_4030", "P1_4020", 1f), MapEdge("P1_4020", "P1_4030", 1f),
                MapEdge("P1_4030", "P1_4035", 1f), MapEdge("P1_4035", "P1_4030", 1f),
                MapEdge("C_OUEST_1060", "P1_4035", 1f), MapEdge("P1_4035", "C_OUEST_1060", 1f),
                MapEdge("P1_1080", "C_OUEST_1080", 1f), MapEdge("C_OUEST_1080", "P1_1080", 1f),
                MapEdge("P1_2000", "C_OUEST_1080", 1f), MapEdge("C_OUEST_1080", "P1_2000", 1f),


                // Couloir Nord
                MapEdge("P1_2010", "C_OUEST_1060", 1f), MapEdge("C_OUEST_1060", "P1_2010", 1f),
                MapEdge("P1_2010", "P1_4040", 1f), MapEdge("P1_4040", "P1_2010", 1f),
                MapEdge("P1_4040", "P1_4050", 1f), MapEdge("P1_4050", "P1_4040", 1f),
                MapEdge("P1_4060", "P1_4050", 1f), MapEdge("P1_4050", "P1_4060", 1f),
                MapEdge("P1_4060", "P1_4070", 1f), MapEdge("P1_4070", "P1_4060", 1f),
                MapEdge("P1_2020", "P1_4070", 1f), MapEdge("P1_4070", "P1_2020", 1f),
                MapEdge("P1_2020", "C_OUEST_4080", 1f), MapEdge("C_OUEST_4080", "P1_2020", 1f),
                MapEdge("P1_2030", "C_OUEST_4080", 1f), MapEdge("C_OUEST_4080", "P1_2030", 1f),

                MapEdge("P1_4071", "P1_4080", 1f), MapEdge("P1_4080", "P1_4071", 1f),
                MapEdge("P1_4072", "P1_4075", 1f), MapEdge("P1_4075", "P1_4072", 1f),
                MapEdge("P1_4073", "P1_4072", 1f), MapEdge("P1_4072", "P1_4073", 1f),
                MapEdge("P1_4075", "P1_4071", 1f), MapEdge("P1_4071", "P1_4075", 1f), // Bob Martin
                MapEdge("P1_4080", "C_OUEST_4080", 1f), MapEdge("C_OUEST_4080", "P1_4080", 1f),

                MapEdge("P1_2030", "Ascenseur_p1_4", 1f), MapEdge("Ascenseur_p1_4", "P1_2030", 1f),
                MapEdge("P1_2060", "P1_2030", 1f), MapEdge("P1_2030", "P1_2060", 1f), // Toilette Homme
                MapEdge("P1_2070", "P1_2060", 1f), MapEdge("P1_2060", "P1_2070", 1f), // Toilette Femme
                MapEdge("Ascenseur_p1_4", "P1_2060", 1f), MapEdge("P1_2060", "Ascenseur_p1_4", 1f), // Toilette Femme
                MapEdge("P1_2070", "Ascenseur_p1_4", 1f), MapEdge("Ascenseur_p1_4", "P1_2070", 1f), // Toilette Femme
                MapEdge("P1_4090", "Ascenseur_p1_4", 1f), MapEdge("Ascenseur_p1_4", "P1_4090", 1f),
                MapEdge("P1_4090", "C_EST_4120", 1f), MapEdge("C_EST_4120", "P1_4090", 1f),

                MapEdge("P1_4110", "C_EST_4120", 1f), MapEdge("C_EST_4120", "P1_4110", 1f),
                MapEdge("P1_4112", "P1_4110", 1f), MapEdge("P1_4110", "P1_4112", 1f),
                MapEdge("P1_4115", "P1_4112", 1f), MapEdge("P1_4112", "P1_4115", 1f),

                MapEdge("P1_4120", "C_EST_4120", 1f), MapEdge("C_EST_4120", "P1_4120", 1f),
                MapEdge("P1_4120", "P1_4130", 1f), MapEdge("P1_4130", "P1_4120", 1f),
                MapEdge("P1_4130", "C_EST_4180", 1f), MapEdge("C_EST_4180", "P1_4130", 1f),

                MapEdge("P1_4140", "C_EST_4180", 1f), MapEdge("C_EST_4180", "P1_4140", 1f),
                MapEdge("P1_4150", "P1_4140", 1f), MapEdge("P1_4140", "P1_4150", 1f),
                MapEdge("P1_4160", "P1_4150", 1f), MapEdge("P1_4150", "P1_4160", 1f),
                MapEdge("P1_4165", "P1_4160", 1f), MapEdge("P1_4160", "P1_4165", 1f),
                MapEdge("P1_4170", "P1_4165", 1f), MapEdge("P1_4165", "P1_4170", 1f),

                MapEdge("P1_4180", "C_EST_4180", 1f), MapEdge("C_EST_4180", "P1_4180", 1f),
                MapEdge("P1_4180", "P1_2080", 1f), MapEdge("P1_2080", "P1_4180", 1f),
                MapEdge("P1_4180", "P1_2090", 1f), MapEdge("P1_2090", "P1_4180", 1f),
                MapEdge("P1_2080", "C_EST_4180", 1f), MapEdge("C_EST_4180", "P1_2080", 1f),
                MapEdge("P1_2090", "P1_2080", 1f), MapEdge("P1_2080", "P1_2090", 1f),
                MapEdge("P1_2090", "P1_4190", 1f), MapEdge("P1_4190", "P1_2090", 1f),
                MapEdge("P1_4200", "P1_4190", 1f), MapEdge("P1_4190", "P1_4200", 1f),
                MapEdge("P1_4200", "C_OUEST_3010", 1f), MapEdge("C_OUEST_3010", "P1_4200", 1f),
                MapEdge("P1_4200", "P1_2110", 1f), MapEdge("P1_2110", "P1_4200", 1f),
                MapEdge("P1_2110", "P1_4190", 1f), MapEdge("P1_4190", "P1_2110", 1f),
                MapEdge("P1_2110", "C_OUEST_3010", 1f), MapEdge("C_OUEST_3010", "P1_2110", 1f),


                // Couloir Nord-Ouest
                MapEdge("P1_3005", "P1_3010", 1f), MapEdge("P1_3010", "P1_3005", 1f),
                MapEdge("P1_3010", "C_OUEST_3010", 1f), MapEdge("C_OUEST_3010", "P1_3010", 1f),
                MapEdge("P1_4205", "C_OUEST_3010", 1f), MapEdge("C_OUEST_3010", "P1_4205", 1f),
                MapEdge("P1_4205", "C_OUEST_4205", 1f), MapEdge("C_OUEST_4205", "P1_4205", 1f),
                MapEdge("P1_4240", "C_OUEST_4205", 1f), MapEdge("C_OUEST_4205", "P1_4240", 1f),
                MapEdge("P1_4250", "P1_4240", 1f), MapEdge("P1_4240", "P1_4250", 1f),
                MapEdge("P1_3030", "P1_4250", 1f), MapEdge("P1_4250", "P1_3030", 1f),
                MapEdge("P1_3030", "Escalier_p1_4_OUEST", 1f), MapEdge("Escalier_p1_4_OUEST", "P1_3030", 1f),

                MapEdge("P1_4210", "C_OUEST_4205", 1f), MapEdge("C_OUEST_4205", "P1_4210", 1f),
                MapEdge("P1_4220", "P1_4210", 1f), MapEdge("P1_4210", "P1_4220", 1f),
                MapEdge("P1_4230", "P1_4220", 1f), MapEdge("P1_4220", "P1_4230", 1f),


                // Couloir OUEST-EST
                MapEdge("P1_4260", "C_OUEST_Centre_Escalier", 1f), MapEdge("C_OUEST_Centre_Escalier", "P1_4260", 1f),
                MapEdge("P1_4270", "P1_4260", 1f), MapEdge("P1_4260", "P1_4270", 1f),
                MapEdge("P1_4280", "P1_4270", 1f), MapEdge("P1_4270", "P1_4280", 1f),
                MapEdge("P1_4280", "C_EST_Centre_Escalier", 1f), MapEdge("C_EST_Centre_Escalier", "P1_4280", 1f),


                // Couloir Sud-Ouest
                MapEdge("P1_5000", "C_OUEST_Centre_Entree", 1f), MapEdge("C_OUEST_Centre_Entree", "P1_5000", 1f),
                MapEdge("P1_5000", "Toilette_P1_6_OUEST", 1f), MapEdge("Toilette_P1_6_OUEST", "P1_5000", 1f),
                MapEdge("P1_5010", "P1_5000", 1f), MapEdge("P1_5000", "P1_5010", 1f),
                MapEdge("P1_5010", "P1_6050", 1f), MapEdge("P1_6050", "P1_5010", 1f),
                MapEdge("P1_5020", "P1_6050", 1f), MapEdge("P1_6050", "P1_5020", 1f),
                MapEdge("P1_6040", "P1_6050", 1f), MapEdge("P1_6050", "P1_6040", 1f),
                MapEdge("P1_6060", "P1_6050", 1f), MapEdge("P1_6050", "P1_6060", 1f),
                MapEdge("C_OUEST_5060", "P1_6040", 1f), MapEdge("P1_6040", "C_OUEST_5060", 1f),
                MapEdge("C_OUEST_5060", "P1_5020", 1f), MapEdge("P1_5020", "C_OUEST_5060", 1f),

                MapEdge("C_OUEST_5060", "C_OUEST_5050", 1f), MapEdge("P1_5020", "C_OUEST_5060", 1f),

                MapEdge("P1_5030", "C_OUEST_5050", 1f), MapEdge("C_OUEST_5050", "P1_5030", 1f),
                MapEdge("P1_5040", "P1_5030", 1f), MapEdge("P1_5030", "P1_5040", 1f), // Sortie de secours OUEST

                MapEdge("P1_5050", "C_OUEST_5060", 1f), MapEdge("C_OUEST_5060", "P1_5050", 1f),
                MapEdge("P1_5060", "P1_5050", 1f), MapEdge("P1_5050", "P1_5060", 1f),
                MapEdge("P1_6380", "P1_5060", 1f), MapEdge("P1_5060", "P1_6380", 1f),
                MapEdge("P1_5070", "P1_6380", 1f), MapEdge("P1_6380", "P1_5070", 1f),
                MapEdge("P1_5080", "P1_5070", 1f), MapEdge("P1_5070", "P1_5080", 1f),
                MapEdge("P1_5090", "P1_5080", 1f), MapEdge("P1_5080", "P1_5090", 1f),
                MapEdge("P1_5090", "P1_5070", 1f), MapEdge("P1_5070", "P1_5090", 1f),
                MapEdge("P1_5090", "C_OUEST_5090", 1f), MapEdge("C_OUEST_5090", "P1_5090", 1f),
                MapEdge("P1_5100", "C_OUEST_5090", 1f), MapEdge("C_OUEST_5090", "P1_5100", 1f),

                MapEdge("Escalier_Secours_p1_6_OUEST", "C_OUEST_5090", 1f), MapEdge("C_OUEST_5090", "Escalier_Secours_p1_6_OUEST", 1f),
                MapEdge("P1_6370", "Escalier_Secours_p1_6_OUEST", 1f), MapEdge("Escalier_Secours_p1_6_OUEST", "P1_6370", 1f),
                MapEdge("P1_6360", "Escalier_Secours_p1_6_OUEST", 1f), MapEdge("Escalier_Secours_p1_6_OUEST", "P1_6360", 1f),
                MapEdge("P1_6360", "P1_6370", 1f), MapEdge("P1_6370", "P1_6360", 1f),

                MapEdge("P1_5100", "P1_6350", 1f), MapEdge("P1_6350", "P1_5100", 1f),
                MapEdge("P1_5110", "P1_5100", 1f), MapEdge("P1_5100", "P1_5110", 1f),
                MapEdge("P1_6350", "C_OUEST_5090", 1f), MapEdge("C_OUEST_5090", "P1_6350", 1f),
                MapEdge("P1_5120", "P1_5110", 1f), MapEdge("P1_5110", "P1_5120", 1f),
                MapEdge("P1_5120", "P1_6340", 1f), MapEdge("P1_6340", "P1_5120", 1f),
                MapEdge("P1_5110", "P1_6340", 1f), MapEdge("P1_6340", "P1_5110", 1f),
                MapEdge("P1_6350", "P1_6340", 1f), MapEdge("P1_6340", "P1_6350", 1f),
                MapEdge("P1_5130", "P1_5120", 1f), MapEdge("P1_5120", "P1_5130", 1f),
                MapEdge("P1_5130", "P1_6140", 1f), MapEdge("P1_6140", "P1_5130", 1f),
                MapEdge("P1_5130", "P1_7140", 1f), MapEdge("P1_7140", "P1_5130", 1f),


                // couloir BLOC 6 center
                MapEdge("P1_6070", "P1_6060", 1f), MapEdge("P1_6060", "P1_6070", 1f),
                MapEdge("P1_6080", "P1_6070", 1f), MapEdge("P1_6070", "P1_6080", 1f), // Alice Leroi
                MapEdge("P1_6090", "C_OUEST_6_Toilette", 1f), MapEdge("C_OUEST_6_Toilette", "P1_6090", 1f),
                MapEdge("P1_6090", "Escalier_p1_6_OUEST", 1f), MapEdge("Escalier_p1_6_OUEST", "P1_6090", 1f),
                MapEdge("P1_6120", "P1_6080", 1f), MapEdge("P1_6080", "P1_6120", 1f),
                MapEdge("P1_6120", "P1_6090", 1f), MapEdge("P1_6090", "P1_6120", 1f),
                MapEdge("P1_6130", "P1_6120", 1f), MapEdge("P1_6120", "P1_6130", 1f),
                MapEdge("P1_6135", "P1_6130", 1f), MapEdge("P1_6130", "P1_6135", 1f),
                MapEdge("P1_6140", "P1_6135", 1f), MapEdge("P1_6135", "P1_6140", 1f),
                MapEdge("P1_6150", "P1_6140", 1f), MapEdge("P1_6140", "P1_6150", 1f),
                MapEdge("P1_6160", "P1_6150", 1f), MapEdge("P1_6150", "P1_6160", 1f),
                MapEdge("P1_6160", "C_OUEST_6180", 1f), MapEdge("C_OUEST_6180", "P1_6160", 1f),
                MapEdge("P1_6170", "C_OUEST_6180", 1f), MapEdge("C_OUEST_6180", "P1_6170", 1f),
                MapEdge("P1_6170", "C_EST_6_Toilette", 1f), MapEdge("C_EST_6_Toilette", "P1_6170", 1f),
                MapEdge("P1_6170", "Escalier_p1_6_EST", 1f), MapEdge("Escalier_p1_6_EST", "P1_6170", 1f),
                MapEdge("P1_6180", "C_OUEST_6180", 1f), MapEdge("C_OUEST_6180", "P1_6180", 1f),
                MapEdge("P1_6180", "C_OUEST_7010", 1f), MapEdge("C_OUEST_7010", "P1_6180", 1f),


                // couloir sud-Est
                MapEdge("P1_7000", "ACCUEIL", 1f), MapEdge("ACCUEIL", "P1_7000", 1f),
                MapEdge("P1_7000", "Toilette_P1_6_EST", 1f), MapEdge("Toilette_P1_6_EST", "P1_7000", 1f),
                MapEdge("P1_7000", "P1_7010", 1f), MapEdge("P1_7010", "P1_7000", 1f),
                MapEdge("P1_7010", "C_OUEST_7010", 1f), MapEdge("C_OUEST_7010", "P1_7010", 1f),
                MapEdge("P1_7050", "C_OUEST_7050", 1f), MapEdge("C_OUEST_7050", "P1_7050", 1f), // Sortie de secours EST
                MapEdge("P1_7060", "C_OUEST_7050", 1f), MapEdge("C_OUEST_7050", "P1_7060", 1f),
                MapEdge("P1_7070", "P1_7060", 1f), MapEdge("P1_7060", "P1_7070", 1f),
                MapEdge("P1_7080", "P1_7070", 1f), MapEdge("P1_7070", "P1_7080", 1f),
                MapEdge("P1_7090", "P1_7080", 1f), MapEdge("P1_7080", "P1_7090", 1f),
                MapEdge("P1_7100", "P1_7090", 1f), MapEdge("P1_7090", "P1_7100", 1f),
                MapEdge("P1_7110", "P1_7090", 1f), MapEdge("P1_7090", "P1_7110", 1f),
                MapEdge("P1_7110", "P1_7100", 1f), MapEdge("P1_7100", "P1_7110", 1f),

                MapEdge("P1_7110", "Escalier_Secours_p1_6_EST", 1f), MapEdge("Escalier_Secours_p1_6_EST", "P1_7110", 1f),
                MapEdge("P1_6310", "Escalier_Secours_p1_6_EST", 1f), MapEdge("Escalier_Secours_p1_6_EST", "P1_6310", 1f),
                MapEdge("P1_6290", "Escalier_Secours_p1_6_EST", 1f), MapEdge("Escalier_Secours_p1_6_EST", "P1_6290", 1f),
                MapEdge("P1_6290", "P1_6310", 1f), MapEdge("P1_6310", "P1_6290", 1f),

                MapEdge("P1_6320", "P1_7110", 1f), MapEdge("P1_7110", "P1_6320", 1f),
                MapEdge("P1_6320", "P1_6330", 1f), MapEdge("P1_6330", "P1_6320", 1f),
                MapEdge("P1_6320", "P1_7120", 1f), MapEdge("P1_7120", "P1_6320", 1f),
                MapEdge("P1_6330", "P1_7130", 1f), MapEdge("P1_7130", "P1_6330", 1f),
                MapEdge("P1_6330", "P1_7120", 1f), MapEdge("P1_7120", "P1_6330", 1f),
                MapEdge("P1_7120", "P1_7110", 1f), MapEdge("P1_7110", "P1_7120", 1f),
                MapEdge("P1_7130", "P1_7120", 1f), MapEdge("P1_7120", "P1_7130", 1f),
                MapEdge("P1_7140", "P1_7130", 1f), MapEdge("P1_7130", "P1_7140", 1f),

            ),

            walls = listOf(),
            poi = listOf(
                PointOfInterest("école de langue française et de culture quebecoise, centre du savoir sur mesure", "P1_2000"),
                PointOfInterest ("Service des ressources financières", "P1_1080"),
                PointOfInterest ("Bureau du registraire", "P1_1050"),
                PointOfInterest ("Centre de référence le cube", "P1_4020"),
                PointOfInterest ("Location de casier", "P1_1045"),
                PointOfInterest ("Entrée ouest", "ENTREE_OUEST"),
                PointOfInterest ("Entrée Nord Ouest", "ENTREE_NORD_OUEST"),
                PointOfInterest ("Entrée Nord Est", "ENTREE_NORD_EST"),
                PointOfInterest ("Entrée Est", "ENTREE_EST"),
                PointOfInterest ("Accueil", "ACCUEIL"),

                //escaliers
                PointOfInterest("Escaliers P1-4 Ouest", "Escalier_p1_4_OUEST"),
                PointOfInterest("Escaliers P1-4 Est", "Escalier_p1_4_EST"),
                PointOfInterest("Escaliers P1-6 Ouest", "Escalier_p1_6_OUEST"),
                PointOfInterest("Escaliers P1-6 Est", "Escalier_p1_6_EST"),
                PointOfInterest("Escaliers P1-6 Secours Ouest", "Escalier_Secours_p1_6_OUEST"),
                PointOfInterest("Escaliers P1-6 Secours Est", "Escalier_Secours_p1_6_EST"),

                //sorties de secours
                PointOfInterest("Sortie de secours Ouest", "Sortie de secours OUEST"),
                PointOfInterest("Sortie de secours Est", "Sortie de secours EST"),
                PointOfInterest("Local de sécurité", "P1_4260"),

                //ascenseurs
                PointOfInterest("Ascenseur P1-4", "Ascenseur_p1_4"),
                PointOfInterest("Ascenseur P1-6 Ouest", "Ascenseur_p1_6_OUEST"),
                PointOfInterest("Ascenseur P1-6 Est", "Ascenseur_p1_6_EST"),

                //toilettes
                PointOfInterest("Toilettes femmes", "P1_2070"),
                PointOfInterest("Toilettes hommes", "P1_2060"),
                PointOfInterest("Toilettes mixtes Ouest", "Toilette_P1_6_OUEST"),
                PointOfInterest("Toilettes mixtes Est", "Toilette_P1_6_EST"),


            ),
        )
}
