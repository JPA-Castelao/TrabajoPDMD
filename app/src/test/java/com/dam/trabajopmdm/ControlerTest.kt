package com.dam.trabajopmdm

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock

/**
 * Clase de test unitario para verificar el comportamiento de ControladorPreference.
 * Usamos Mocks para simular el Context y SharedPreferences, que son dependencias de Android.
 */
class ControladorPreferenceTest {

    // 1. Declaración de Mocks
    // 'mock()' es una función de la librería Mockito-Kotlin que crea objetos simulados.
    private val mockContext: Context = mock()
    private val mockSharedPreferences: SharedPreferences = mock()
    private val mockEditor: SharedPreferences.Editor = mock()

    // Constantes copiadas de la clase original para usarlas en los tests
    private val PREFS_NAME = "record_preferences"
    private val KEY_RECORD = "record"
    private val KEY_FECHA = "fecha"

    /**
     * El método 'setup' se ejecuta antes de CADA test (@Test).
     * Aquí definimos el comportamiento esperado de los Mocks.
     */
    @Before
    fun setup() {
        // Simulación: Cuando se pide el fichero de preferencias (getSharedPreferences),
        // siempre devolvemos nuestro mock de SharedPreferences.
        `when`(mockContext.getSharedPreferences(eq(PREFS_NAME), eq(Context.MODE_PRIVATE)))
            .thenReturn(mockSharedPreferences)

        // Simulación: Cuando se llama a sharedPreferences.edit(), devolvemos nuestro mock de Editor.
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)

        // Simulación de los métodos de guardado (putInt, putString).
        // Deben devolver el propio editor para permitir el encadenamiento de llamadas.
        `when`(mockEditor.putInt(any(), any())).thenReturn(mockEditor)
        `when`(mockEditor.putString(any(), any())).thenReturn(mockEditor)

        // Simulación de apply(): La extensión KTX edit {} llama internamente a apply().
        // Configuramos el mockEditor para que no haga nada ('then {}') cuando se llama a apply().
        `when`(mockEditor.apply()).then {}
    }

    // -----------------------------------------------------------------------------------
    // Tests para la función obtenerRecord
    // -----------------------------------------------------------------------------------

    @Test
    fun obtenerRecord_retornaValorGuardado() {
        // CASO 1: La preferencia existe.
        val recordEsperado = 500

        // Configurar el mock: Cuando se llama a getInt con la clave, retorna el valor que guardamos.
        `when`(mockSharedPreferences.getInt(eq(KEY_RECORD), any()))
            .thenReturn(recordEsperado)

        // Ejecutar la función
        val recordObtenido = ControladorPreference.obtenerRecord(mockContext)

        // Verificar: El valor obtenido debe coincidir con el valor simulado.
        assertEquals(recordEsperado, recordObtenido)
    }

    @Test
    fun obtenerRecord_retornaCeroPorDefecto() {
        // CASO 2: La preferencia no existe (se espera el valor por defecto).
        val valorPorDefectoEsperado = 0

        // Configurar el mock: Aseguramos que el valor devuelto es el por defecto (0)
        // cuando se pasa 0 como segundo argumento.
        `when`(mockSharedPreferences.getInt(eq(KEY_RECORD), eq(0)))
            .thenReturn(valorPorDefectoEsperado)

        // Ejecutar y verificar
        val recordObtenido = ControladorPreference.obtenerRecord(mockContext)
        assertEquals(valorPorDefectoEsperado, recordObtenido)
    }

    // -----------------------------------------------------------------------------------
    // Tests para la función actualizarRecord
    // -----------------------------------------------------------------------------------

    @Test
    fun actualizarRecord_guardaValorCorrectoYHaceApply() {
        val nuevoRecord = 999

        // Ejecutar la función
        ControladorPreference.actualizarRecord(mockContext, nuevoRecord)

        // Verificaciones (Assertions sobre el comportamiento):

        // 1. Verificar que se llamó a edit() para empezar la transacción.
        verify(mockSharedPreferences).edit()

        // 2. Verificar que se llamó a putInt con la clave correcta (KEY_RECORD) y el nuevo valor.
        verify(mockEditor).putInt(eq(KEY_RECORD), eq(nuevoRecord))

        // 3. Verificar que se llamó a apply() para guardar los cambios de forma asíncrona
        // (esto es implícito en la extensión KTX 'edit {}').
        verify(mockEditor).apply()
    }

    // -----------------------------------------------------------------------------------
    // Tests para la función obtenerFechayHora
    // -----------------------------------------------------------------------------------

    @Test
    fun obtenerFechayHora_retornaFechaGuardada() {
        // CASO 1: La fecha existe.
        val fechaEsperada = "2025-12-05 13:00:00"

        // Configurar el mock: Cuando se llame a getString con la clave KEY_FECHA, retorna el valor simulado.
        `when`(mockSharedPreferences.getString(eq(KEY_FECHA), any()))
            .thenReturn(fechaEsperada)

        // Ejecutar la función
        val fechaObtenida = ControladorPreference.obtenerFechayHora(mockContext)

        // Verificar el resultado
        assertEquals(fechaEsperada, fechaObtenida)
    }

    @Test
    fun obtenerFechayHora_retornaValorPorDefecto() {
        // CASO 2: La fecha no existe.
        val valorPorDefectoEsperado = "fecha"

        // Configurar el mock: Cuando se llame a getString, retorna el valor por defecto ("fecha").
        `when`(mockSharedPreferences.getString(eq(KEY_FECHA), eq("fecha")))
            .thenReturn(valorPorDefectoEsperado)

        // Ejecutar la función
        val fechaObtenida = ControladorPreference.obtenerFechayHora(mockContext)

        // Verificar el resultado
        assertEquals(valorPorDefectoEsperado, fechaObtenida)
    }
}