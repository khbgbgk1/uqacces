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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
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
 * @param heading The current compass heading, used to rotate the map.
 */
@Composable
fun MapView(
    mapData: MapData,
    mapBackground: MapBackground,
    modifier: Modifier = Modifier,
    pathNodeIds: List<String> = emptyList(),
    heading: Float,
    debugNodes: Boolean = false,
) {
    var scale by remember { mutableStateOf(2.5f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val nodesById = remember(mapData.nodes) { mapData.nodes.associateBy { it.id } }
    val textMeasurer = rememberTextMeasurer()

    val backgroundPainter = painterResource(id = mapBackground.resourceId)
    val imageSize = Size(mapBackground.width, mapBackground.height)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale
                    scale = (scale * zoom).coerceIn(1f, 5f)

                    // Ajuster l'offset pour zoomer vers le point touché
                    val scaleChange = scale / oldScale
                    offset = (offset + centroid) * scaleChange - centroid + pan
                }
            }
    ) {
        val canvasCenter = center
        val mapCenter = Offset(imageSize.width / 2, imageSize.height / 2)

        withTransform({
            // 1. Se déplacer au centre du canvas
            translate(left = canvasCenter.x, top = canvasCenter.y)

            // 2. Appliquer la rotation autour de ce point
            rotate(degrees = -heading)

            // 3. Appliquer le zoom
            scale(scale, scale)

            // 4. Appliquer le déplacement utilisateur
            translate(left = offset.x, top = offset.y)

            // 5. Centrer la map (position initiale)
            translate(left = -mapCenter.x, top = -mapCenter.y)
        }) {
            // Dessiner le fond
            with(backgroundPainter) {
                draw(size = imageSize)
            }

            // Dessiner les nœuds en mode debug
            if (debugNodes) {
                mapData.nodes.forEach { node ->
                    val color = when {
                        node.type.startsWith("Classe", ignoreCase = true) -> Color.Yellow
                        node.type.startsWith("Corridor", ignoreCase = true) -> Color.Magenta
                        node.type.startsWith("Ascenseur", ignoreCase = true) -> Color.Green
                        node.type.startsWith("Escalier", ignoreCase = true) -> Color.Cyan
                        node.type.startsWith("Secours", ignoreCase = true) -> Color.Black
                        else -> Color.Red
                    }

                    drawCircle(
                        color = color,
                        radius = 6f / scale, // Ajuster la taille avec le zoom
                        center = node.position
                    )
                }
            }

            // Dessiner le chemin
            if (pathNodeIds.size > 1) {
                for (i in 0 until pathNodeIds.size - 1) {
                    val startNode = nodesById[pathNodeIds[i]]
                    val endNode = nodesById[pathNodeIds[i + 1]]
                    if (startNode != null && endNode != null) {
                        drawLine(
                            color = Color.Blue,
                            start = startNode.position,
                            end = endNode.position,
                            strokeWidth = 5f / scale, // Ajuster l'épaisseur avec le zoom
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f / scale, 10f / scale), 0f)
                        )
                    }
                }
            }
        }
    }
}