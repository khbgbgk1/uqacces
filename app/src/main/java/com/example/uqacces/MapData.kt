package com.example.uqacces

import androidx.compose.ui.geometry.Offset

/**
 * Represents a point of interest on the map, like a classroom or a service point.
 *
 * @param id A unique identifier for the node.
 * @param name The display name of the node (e.g., "Room 101").
 * @param position The coordinates of the node on the map.
 */
data class MapNode(
    val id: String,
    val name: String,
    val position: Offset
)

/**
 * Represents a connection (an edge) between two nodes in our map graph.
 *
 * @param startNodeId The ID of the starting node.
 * @param endNodeId The ID of the ending node.
 */
data class MapEdge(
    val startNodeId: String,
    val endNodeId: String
)

/**
 * Represents a physical wall on the map.
 *
 * @param start The starting coordinate of the wall.
 * @param end The ending coordinate of the wall.
 */
data class Wall(
    val start: Offset,
    val end: Offset
)

/**
 * A container for all the map data.
 *
 * @param nodes A list of all the nodes on the map.
 * @param edges A list of all the connections between nodes.
 * @param walls A list of all the walls.
 */
data class MapData(
    val nodes: List<MapNode>,
    val edges: List<MapEdge>,
    val walls: List<Wall>
)
