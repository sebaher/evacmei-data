# Mejoras para tu app de evacuación minera (Android Studio)

Este documento propone una evolución de la app para emergencias en mina subterránea, manteniendo tu lógica principal:

- **Entrada:** ubicación actual + foco de incendio.
- **Salida:** destino/acción de evacuación recomendada.
- **Actualización:** botón para refrescar cambios en la combinación ubicación-foco.

## 1) Mejoras visuales (imágenes + movimiento)

### Imágenes recomendadas
- **Mapa simplificado por sectores/niveles** (sin sobrecargar detalle).
- **Íconos por estado**:
  - 🔥 foco de incendio
  - ✅ ruta segura
  - ⚠️ ruta en revisión
  - 🚫 ruta bloqueada
- **Foto/diagrama del refugio o estación de reunión** para confirmar destino.

### Movimiento/animaciones útiles
- **Pulsación del foco de incendio** (llama intermitente) para visibilidad inmediata.
- **Animación de ruta** (trazo progresivo del camino sugerido).
- **Transición de estado** cuando llega una actualización (por ejemplo, de verde a rojo con animación suave).
- **Microinteracción del botón “Actualizar”** con rotación del icono de sync y barra de progreso.

> Recomendación: usar **Jetpack Compose + Lottie** para animaciones livianas y mantenibles.

## 2) Flujo propuesto

1. Usuario selecciona **Ubicación actual**.
2. Usuario selecciona **Foco de incendio**.
3. App calcula regla `ubicación + foco -> destino`.
4. Se muestra:
   - destino recomendado,
   - prioridad/riesgo,
   - tiempo estimado de desplazamiento,
   - última actualización.
5. Botón **Actualizar** solicita nuevas reglas y recalcula en pantalla.

## 3) Botón “Actualizar” robusto

- Al pulsar:
  1. Llama al endpoint (o descarga archivo de reglas).
  2. Valida esquema JSON.
  3. Guarda en caché local (Room/DataStore).
  4. Recalcula destino en la UI.
- Si no hay red:
  - usar último set de reglas válido,
  - mostrar banner: “Mostrando datos locales (hh:mm)”.

## 4) Ideas de mejora avanzada

- **Modo offline crítico**: toda operación base sin internet.
- **Notificaciones push** ante cambios urgentes de rutas.
- **Modo voz**: lectura en voz alta del destino y pasos.
- **Modo guantes/minería**:
  - botones grandes,
  - alto contraste,
  - flujo en 2-3 toques.
- **Trazabilidad**: bitácora de decisiones (timestamp, regla usada, versión de datos).

## 5) Paquete técnico sugerido

- UI: Jetpack Compose
- Estado: ViewModel + StateFlow
- Datos locales: Room o DataStore
- Red: Retrofit + Kotlin Serialization
- Tareas en segundo plano: WorkManager
- Animación: Lottie Compose

## 6) Estructura mínima de datos

```json
{
  "version": "2026.02.01",
  "rules": [
    {
      "ubicacion": "Galeria_Norte_2",
      "foco": "Chancador_Primario",
      "destino": "Refugio_R3",
      "riesgo": "ALTO",
      "nota": "Evitar nivel -340 por humo"
    }
  ]
}
```

## 7) Métricas recomendadas

- Tiempo de respuesta UI tras pulsar **Actualizar**.
- % de consultas resueltas offline.
- Tasa de cambio de destino por actualización.
- Tiempo promedio hasta decisión de evacuación.

