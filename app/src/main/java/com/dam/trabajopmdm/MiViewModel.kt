package com.dam.trabajopmdm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dam.mvvm_basic.Estados
import kotlinx.coroutines.flow.MutableStateFlow

class MiViewModel(): ViewModel() {
    //etiqueta para logcat
    private val TAG_LOG = "midebug"

    //estados del juego
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)

    var _numbers = mutableStateOf(0)


}