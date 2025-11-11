package com.dam.mvvm_basic

import androidx.compose.ui.graphics.Color

/**
 * Clase para almacenar los datos del juego
 */
object Datos {
    var numero = 0
}

/**
 * Colores utilizados
 */
enum class Colores(val color: Color, val txt: String) {
    CLASE_VERDE(color = Color.Green, txt = "Do alto"),
    CLASE_ROJO(color = Color.Red, txt = "Mi medio-alto"),
    CLASE_AZUL(color = Color.Blue, txt = "Sol medio-bajo"),
    CLASE_AMARILLO(color = Color.Yellow, txt = "Do bajo"),
    CLASE_START(color = Color.Magenta, txt = "Start")
}

/**
 * Estados del juego
 */
enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true),
    FINALIZANDO(boton_activo = false, start_activo = false)
}