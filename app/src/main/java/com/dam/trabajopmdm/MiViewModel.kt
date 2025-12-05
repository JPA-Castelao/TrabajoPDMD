package com.dam.trabajopmdm

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dam.mvvm_basic.Datos
import com.dam.mvvm_basic.Estados
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class MiViewModel(application: Application) : AndroidViewModel(application) {


    //variable record
    val record = MutableStateFlow<Int>(0)

    //variable fecha y hora
    val fecha = MutableStateFlow<String>("")

    init {

        // mostramos el record guardado
        Log.d("_PREF", "Record guardado: ${obtenerRecord()}")
        // mandamos un record al ViewModel
        // el ViewModel decide si es o no record
        // este valor lo podemos ir cambiando para observar si actualiza o no el recordç
        //ControladorPreference.actualizarRecord(context = getApplication(), nuevoRecord = 0)
        record.value = obtenerRecord()
    }
    //observable de record

    /**
     * Obtiene el record actual.
     * @return El record actual.
     */
    fun obtenerRecord(): Int {
        record.value = ControladorPreference.obtenerRecord(getApplication())
        Log.d("_PREF", "Record: ${(record.value)}")
        return record.value
    }

    /**
     * Obtiene la fecha y hora actuales.
     * @return La fecha actual.
     */
    fun obtenerFechayHora(): String {

        fecha.value = ControladorPreference.obtenerFechayHora(context = getApplication()).toString()
        Log.d("_PREF", "FechayHora: ${(fecha.value)}")
        return fecha.value

    }


    // variable estados del juego
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)

    //variable numero random
    var _numbers = mutableStateOf(0)

    //variable puntuacion
    val puntuacion = MutableStateFlow<Int>(0)


    //variable ronda
    var ronda = MutableStateFlow<Int>(1)

    //variable posicion
    var posicion = 0

    //Función para crear un número random
    fun numeroRandom() {
        estadoActual.value = Estados.GENERANDO
        Log.d("ViewModel", "Estado generando")
        _numbers.value = (0..3).random()
        Log.d("ViewModel", "Número random generado: $_numbers")
        actualizarNumero(_numbers.value)
    }

    //Funcion para actualizar el número random
    fun actualizarNumero(numero: Int) {
        Log.d("ViewModel", "Actualizando el numero de la clase Datos")
        Datos.numero.add(numero)
        estadoActual.value = Estados.ADIVINANDO
        mostrarSerie(Datos.numero)
    }

    fun mostrarSerie(serie: ArrayList<Int>) {
        Log.d("ViewModel", "Estado adivinando, secuencia: $serie")
    }

    fun corregirOpcion(numeroColor: Int): Boolean {
        viewModelScope.launch {
            delay(1500)
        }
        Log.d("ViewModel", "Combrobando si la opción escogida es correcta...")
        return if (numeroColor == Datos.numero[posicion]) {
            Log.d("ViewModel", "ES CORRECTO !")
            posicion++
            if (Datos.numero.size == posicion) {
                cambiarRonda()
            }
            puntuacion.value = puntuacion.value.plus(1)

            true
        } else {
            Log.d("ViewModel", "ERROR, HAS PERDIDO")
            derrota(puntuacion.value)
            false
        }
    }

    fun cambiarRonda() {
        posicion = 0
        ronda.value = ronda.value.plus(1)
        numeroRandom()
    }

    /**
     * Comprueba si un posible record es mayor que el actual.
     * Obtiene la fecha y hora del nuevo record
     * @param posibleRecord El posible record a comprobar.
     */

    fun derrota(posibleRecord: Int) {

        if (posibleRecord > obtenerRecord()) {
            ControladorPreference.actualizarRecord(getApplication(), posibleRecord)
            record.value = posibleRecord
            fecha.value = LocalDateTime.now().toString()
        }

        puntuacion.value = 0
        posicion = 0
        ronda.value = 1
        estadoActual.value = Estados.INICIO
        Datos.numero = ArrayList()
    }


}