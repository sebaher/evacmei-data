package com.evacmei.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Pantalla de ejemplo para mostrar mejoras visuales:
 * - Indicador animado del foco de incendio
 * - Botón actualizar con estado de carga
 * - Tarjeta de destino recomendado
 */
@Composable
fun EmergenciaMineScreen(
    ubicacion: String,
    foco: String,
    destino: String,
    ultimaActualizacion: String,
    loading: Boolean,
    mostrarAlertaOffline: Boolean,
    onActualizar: () -> Unit,
) {
    val pulso = rememberInfiniteTransition(label = "foco")
    val alpha by pulso.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "alphaFoco",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("EvacMEI - Emergencia", style = MaterialTheme.typography.headlineSmall)

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Foco:")
            Text(
                text = "🔥 $foco",
                modifier = Modifier.alpha(alpha),
                color = Color.Red,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Ubicación actual: $ubicacion")
                Text("Destino recomendado: $destino", style = MaterialTheme.typography.titleMedium)
                Text("Última actualización: $ultimaActualizacion")
            }
        }

        AnimatedVisibility(visible = mostrarAlertaOffline) {
            Text(
                text = "⚠ Mostrando datos locales por falta de red",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF3CD), RoundedCornerShape(10.dp))
                    .padding(10.dp),
                color = Color(0xFF7A4E00),
            )
        }

        Button(onClick = onActualizar, enabled = !loading) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.padding(end = 8.dp))
                Text("Actualizando...")
            } else {
                Text("Actualizar")
            }
        }
    }
}
