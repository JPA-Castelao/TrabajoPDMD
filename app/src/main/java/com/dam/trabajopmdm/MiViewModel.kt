package com.dam.trabajopmdm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dam.mvvm_basic.Datos
import com.dam.mvvm_basic.Estados
import kotlinx.coroutines.flow.MutableStateFlow

class MiViewModel(): ViewModel() {
    // variable estados del juego
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)

    //variable numero random
    var _numbers = mutableStateOf(0)

    //variable puntuacion
    val puntuacion = MutableStateFlow<Int?>(0)

    //Función para crear un número random
    fun numeroRandom(){
        estadoActual.value = Estados.GENERANDO
        Log.d("ViewModel","Estado generando")
        _numbers.value = (0..3).random()
        Log.d("ViewModel","Número random generado: $_numbers")
        actualizarNumero(_numbers.value)
    }

    //Funcion para actualizar el número random
    fun actualizarNumero(numero: Int){
        Log.d("ViewModel","Actualizando el numero de la clase Datos")
        Datos.numero = numero
        estadoActual.value = Estados.ADIVINANDO
        Log.d("ViewModel", "Estado adivinando")
    }

    fun corregirOpción(numeroColor: Int): Boolean{
        Log.d("ViewModel", "Comprobando si la opción es correcta")
        return if (numeroColor == Datos.numero){
            Log.d("MiViewModel","Es correcto")
            numeroRandom()
            puntuacion.value = puntuacion.value?.plus(1)
            true
        }else{
            Log.d("MiViewModel","Error, has perdido")
            derrota()
            false
        }
    }

    fun derrota(){
        puntuacion.value = 0
        estadoActual.value = Estados.INICIO
    }
}