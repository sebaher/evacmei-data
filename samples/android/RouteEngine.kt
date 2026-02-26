package com.evacmei.app.domain

data class Rule(
    val ubicacion: String,
    val foco: String,
    val destino: String,
    val riesgo: String,
)

class RouteEngine(private val rules: List<Rule>) {
    fun resolverDestino(ubicacion: String, foco: String): Rule? {
        return rules.firstOrNull {
            it.ubicacion.equals(ubicacion, ignoreCase = true) &&
                it.foco.equals(foco, ignoreCase = true)
        }
    }
}
