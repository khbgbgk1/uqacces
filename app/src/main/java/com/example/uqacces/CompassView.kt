package com.example.uqacces

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

/**
 * A composable that displays a compass icon pointing to the current heading.
 *
 * @param heading The current compass heading in degrees (0 to 360), where 0 is North.
 * @param modifier A modifier to be applied to the compass icon.
 */
@Composable
fun CompassView(heading: Float, modifier: Modifier = Modifier) {
    val targetOffset = 325f // L'angle Vers lequel le compas doit pointer

    val angleDifference = (heading - targetOffset + 360f) % 360f
    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Navigation,
            contentDescription = "Boussole",
            modifier = Modifier
                .padding(16.dp)
                .rotate(angleDifference), // Rotate the icon to point North
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}
