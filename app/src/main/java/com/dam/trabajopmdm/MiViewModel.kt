package com.dam.trabajopmdm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dam.mvvm_basic.Estados
import kotlinx.coroutines.flow.MutableStateFlow

class MiViewModel(): ViewModel() {
    // variable estados del juego
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)

    //variable numero random
    var _numbers = mutableStateOf(0)

    //variable puntuacion
    val puntuacion = MutableStateFlow<Int?>(0)




}