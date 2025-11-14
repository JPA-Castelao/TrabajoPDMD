package com.dam.trabajopmdm

import android.health.connect.datatypes.ExerciseCompletionGoal.ActiveCaloriesBurnedGoal
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTargetMarker
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dam.mvvm_basic.Colores
import com.dam.mvvm_basic.Estados

//interfaz de usuario
//Modificado desde cero

@Composable
fun IU(miViewModel: MiViewModel) {
    //para q sea más fácil la etiqueta del log
    val TAG_LOG: String = "miDebug"

    //variable para el estado del boton
    val estadoActual = miViewModel.estadoActual.collectAsState().value

    @Composable
    fun Boton(viewModel: MiViewModel, enum_color: Colores) {
        val activo = viewModel.estadoActual.collectAsState().value
        Button(
            enabled = activo.boton_activo,
            colors = ButtonDefaults.buttonColors(enum_color.color),
            onClick = {
                Log.d("Juego", enum_color.txt + " numero: " + enum_color.ordinal)
                viewModel.corregirOpcion(enum_color.ordinal)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.size(150.dp).padding(15.dp)
        ) {
            Text(
                text = enum_color.txt,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }

    @Composable
    fun Botonera(miViewModel: MiViewModel) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Boton(miViewModel, Colores.CLASE_ROJO)
                Boton(miViewModel, Colores.CLASE_AZUL)
            }
            Row {
                Boton(miViewModel, Colores.CLASE_AMARILLO)
                Boton(miViewModel, Colores.CLASE_VERDE)
            }
        }
    }

    @Composable
    fun Boton_Start(miViewModel: MiViewModel, enum_color: Colores) {
        val estado = miViewModel.estadoActual.collectAsState().value
        Button(
            enabled = estado.start_activo,
            onClick = {
                Log.d("Juego", "Empieza la partida")
                miViewModel.numeroRandom()
            }) {
            Text(text = "Start")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun IUPreview() {
        IU(miViewModel = MiViewModel())
    }
}