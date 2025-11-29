package com.example.uqacces

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp

/**
 * A composable that renders the map and handles user gestures for zooming and panning.
 *
 * @param mapData The data for the map to be rendered.
 * @param modifier A modifier to be applied to the canvas.
 * @param pathNodeIds The list of node IDs representing the path to draw.
 */
@Composable
fun MapView(
    mapData: MapData,
    mapBackground: MapBackground,
    modifier: Modifier = Modifier,
    debugNodes: Boolean = false,
    pathNodeIds: List<String> = emptyList()
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val nodesById = remember(mapData.nodes) { mapData.nodes.associateBy { it.id } }
    val textMeasurer = rememberTextMeasurer()

    val backgroundPainter = painterResource(id = mapBackground.resourceId)
    val imageSize = Size(mapBackground.width, mapBackground.height)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.5f, 5f) // Clamp scale
                    offset += pan
                }
            }
    ) { 
        withTransform({
            translate(left = offset.x, top = offset.y)
            scale(scale, scale)
        }) {
            with(backgroundPainter) {
               draw(size = imageSize) // Dessine l'image en utilisant les dimensions définies dans MapBackground
            }
            // Draw walls
//            mapData.walls.forEach { wall ->
//                drawLine(
//                    color = Color.Black,
//                    start = wall.start,
//                    end = wall.end,
//                    strokeWidth = 2f
//                )
//            }

            // Draw nodes
            if (debugNodes) {
                mapData.nodes.forEach { node ->
                    if (node.type.startsWith("Classe", ignoreCase = true)) {
//                        drawText(
//                            textMeasurer = textMeasurer,
//                            text = node.name,
//                            topLeft = Offset(node.position.x + 10, node.position.y - 30),
//                            style = TextStyle(
//                                color = Color.Black,
//                                fontSize = 12.sp / scale // Adjust font size with zoom
//                            )
//                        )
                        drawCircle(
                            color = Color.Yellow,
                            radius = 6f,
                            center = node.position
                        )
                    } else if(node.type.startsWith("Corridor", ignoreCase = true)) {
                        drawCircle(
                            color = Color.Magenta,
                            radius = 6f,
                            center = node.position
                        )
                    }

                    else if(node.type.startsWith("Ascenseur", ignoreCase = true)) {
                        drawCircle(
                            color = Color.Green,
                            radius = 6f,
                            center = node.position
                        )
                    }

                    else if(node.type.startsWith("Escalier", ignoreCase = true)) {
                        drawCircle(
                            color = Color.Cyan,
                            radius = 6f,
                            center = node.position
                        )
                    }

                    else {
                        // For corridors, entrances, etc., just draw a small circle
                        drawCircle(
                            color = Color.Red,
                            radius = 6f,
                            center = node.position
                        )
                    }
                }
            }

            // Draw path
            if (pathNodeIds.size > 1) {
                for (i in 0 until pathNodeIds.size - 1) {
                    val startNode = nodesById[pathNodeIds[i]]
                    val endNode = nodesById[pathNodeIds[i + 1]]
                    if (startNode != null && endNode != null) {
                        drawLine(
                            color = Color.Blue,
                            start = startNode.position,
                            end = endNode.position,
                            strokeWidth = 5f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                        )
                    }
                }
            }
        }
    }
}