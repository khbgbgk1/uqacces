package com.example.uqacces

import java.util.PriorityQueue

/**
 * Finds the shortest path between two nodes in a graph using the A* algorithm.
 *
 * @param mapData The map data containing the graph's nodes and edges.
 * @param startNodeId The ID of the starting node.
 * @param endNodeId The ID of the ending node.
 * @return A list of node IDs representing the shortest path from the start node to the end node,
 *         or an empty list if no path is found.
 */
fun findShortestPath(mapData: MapData, startNodeId: String, endNodeId: String): List<String> {
    val nodes = mapData.nodes.associateBy { it.id }
    val edges = mapData.edges.groupBy { it.startNodeId }.mapValues { (_, edges) -> edges.map { it.endNodeId } }

    val cameFrom = mutableMapOf<String, String>()
    val costSoFar = mutableMapOf<String, Float>().withDefault { Float.MAX_VALUE }
    val priorityQueue = PriorityQueue<Pair<String, Float>>(compareBy { it.second })

    costSoFar[startNodeId] = 0f
    priorityQueue.add(startNodeId to 0f)

    while (priorityQueue.isNotEmpty()) {
        val (currentId) = priorityQueue.poll()!!

        if (currentId == endNodeId) {
            // Reconstruct path
            val path = mutableListOf<String>()
            var current = endNodeId
            while (current != startNodeId) {
                path.add(current)
                current = cameFrom[current]!!
            }
            path.add(startNodeId)
            return path.asReversed()
        }

        edges[currentId]?.forEach { neighborId ->
            val newCost = costSoFar.getValue(currentId) + 1 // Assuming edge weight is 1
            if (newCost < costSoFar.getValue(neighborId)) {
                costSoFar[neighborId] = newCost
                val priority = newCost + heuristic(nodes[neighborId]!!, nodes[endNodeId]!!)
                priorityQueue.add(neighborId to priority)
                cameFrom[neighborId] = currentId
            }
        }
    }

    return emptyList() // No path found
}

/**
 * A heuristic function for the A* algorithm. It calculates the straight-line distance
 * between two nodes.
 */
private fun heuristic(a: MapNode, b: MapNode): Float {
    return (a.position - b.position).getDistance()
}
