package com.dam.trabajopmdm


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock

class ControladorPreferenceTest {

    // 1. Mocks de las dependencias de Android
    private val mockContext: Context = mock()
    private val mockSharedPreferences: SharedPreferences = mock()
    private val mockEditor: SharedPreferences.Editor = mock()

    // Constantes de la clase original
    private val PREFS_NAME = "record_preferences"
    private val KEY_RECORD = "record"
    private val KEY_FECHA = "fecha"

    @Before
    fun setup() {
        // Configuramos el comportamiento de los Mocks antes de cada test:

        // Cuando se llama a context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE),
        // devolvemos nuestro mock de SharedPreferences.
        `when`(mockContext.getSharedPreferences(eq(PREFS_NAME), eq(Context.MODE_PRIVATE)))
            .thenReturn(mockSharedPreferences)

        // Cuando se llama a sharedPreferences.edit(), devolvemos nuestro mock de Editor.
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)

        // Hacemos que el método putInt() del editor devuelva el editor para encadenar llamadas.
        `when`(mockEditor.putInt(any(), any())).thenReturn(mockEditor)
        // Hacemos que el método putString() del editor devuelva el editor para encadenar llamadas.
        `when`(mockEditor.putString(any(), any())).thenReturn(mockEditor)

        // El método apply() de la extensión KTX (que usa apply() internamente) no debe hacer nada.
        // La extensión KTX internamente llama a edit().putX().apply() o edit().putX().commit()
        // Para simplificar, asumimos que la extensión KTX llama a apply() en el editor.
        `when`(mockEditor.apply()).then {} // No hace nada, solo satisface la llamada
    }

    // --- Tests para obtenerRecord ---

    @Test
    fun obtenerRecord_retornaValorGuardado() {
        val recordEsperado = 500

        // Configurar el mock: cuando se llame a getInt con la clave, retorna el valor esperado
        `when`(mockSharedPreferences.getInt(eq(KEY_RECORD), any()))
            .thenReturn(recordEsperado)

        // Ejecutar la función a testear
        val recordObtenido = ControladorPreference.obtenerRecord(mockContext)

        // Verificar el resultado
        assertEquals(recordEsperado, recordObtenido)
    }

    @Test
    fun obtenerRecord_retornaCeroPorDefecto() {
        val valorPorDefectoEsperado = 0

        // Configurar el mock: cuando se llame a getInt, retorna 0 (el valor por defecto)
        `when`(mockSharedPreferences.getInt(eq(KEY_RECORD), eq(0)))
            .thenReturn(valorPorDefectoEsperado)

        // Ejecutar y verificar
        val recordObtenido = ControladorPreference.obtenerRecord(mockContext)
        assertEquals(valorPorDefectoEsperado, recordObtenido)
    }

    // --- Tests para actualizarRecord ---

    @Test
    fun actualizarRecord_guardaValorCorrecto() {
        val nuevoRecord = 999

        // Ejecutar la función
        ControladorPreference.actualizarRecord(mockContext, nuevoRecord)

        // Verificar que:
        // 1. Se llamó al método edit()
        verify(mockSharedPreferences).edit()

        // 2. Se llamó al método putInt con la clave y el valor correctos
        verify(mockEditor).putInt(eq(KEY_RECORD), eq(nuevoRecord))

        // 3. Se llamó a apply() para guardar los cambios (implícito en la extensión KTX edit {})
        verify(mockEditor).apply()
    }

    // --- Tests para obtenerFechayHora ---

    @Test
    fun obtenerFechayHora_retornaFechaGuardada() {
        val fechaEsperada = "2025-12-05 13:00:00"

        // Configurar el mock: cuando se llame a getString con la clave, retorna la fecha esperada
        `when`(mockSharedPreferences.getString(eq(KEY_FECHA), any()))
            .thenReturn(fechaEsperada)

        // Ejecutar la función
        val fechaObtenida = ControladorPreference.obtenerFechayHora(mockContext)

        // Verificar el resultado
        assertEquals(fechaEsperada, fechaObtenida)
    }

    @Test
    fun obtenerFechayHora_retornaValorPorDefecto() {
        val valorPorDefectoEsperado = "fecha"

        // Configurar el mock: cuando se llame a getString con la clave, retorna el valor por defecto
        `when`(mockSharedPreferences.getString(eq(KEY_FECHA), eq("fecha")))
            .thenReturn(valorPorDefectoEsperado)

        // Ejecutar la función
        val fechaObtenida = ControladorPreference.obtenerFechayHora(mockContext)

        // Verificar el resultado
        assertEquals(valorPorDefectoEsperado, fechaObtenida)
    }
}