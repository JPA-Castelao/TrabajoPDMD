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

    //botones en horizontal
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        Column {
            Row{
                //creo un boton rojo
                Boton(miViewModel, Colores.CLASE_ROJO)

                //creo un boton verde
                Boton(miViewModel, Colores.CLASE_VERDE)

            }
            Row{
                //creo un boton azul
                Boton(miViewModel, Colores.CLASE_AZUL)


                //creo un boton amarillo
                Boton(miViewModel, Colores.CLASE_AMARILLO)

            }
        }
        //creo boton start
        Boton_Start(miViewModel, Colores.CLASE_START)
    }
}

@Composable
fun Boton(miViewModel: MiViewModel, enum_color: Colores) {

    // para que sea mas facil la etiqueta del log
    val TAG_LOG: String = "miDebug"

    // separador entre botones
    Spacer(modifier = Modifier.size(10.dp))

    Button(
        // utilizamos el color del enum
        colors =  ButtonDefaults.buttonColors(enum_color.color),
        onClick = {
            Log.d(TAG_LOG, "Dentro del boton: ${enum_color.ordinal}")
            miViewModel.corregirOpcion(enum_color.ordinal)
        },
        modifier = Modifier
            .size((80).dp, (40).dp)
    ) {
        // utilizamos el texto del enum
        Text(text = enum_color.txt, fontSize = 10.sp)
    }
}

@Composable
fun Boton_Start(miViewModel: MiViewModel, enum_color: Colores) {

    // para que sea mas facil la etiqueta del log
    val TAG_LOG: String = "miDebug"
    // separador entre botones
    Spacer(modifier = Modifier.size(40.dp))
    Button(
        // utilizamos el color del enum
        colors =  ButtonDefaults.buttonColors(enum_color.color),
        onClick = {
            Log.d(TAG_LOG, "Dentro del Start")
            miViewModel.numeroRandom()
        },
        modifier = Modifier
            .size((100).dp, (40).dp)
    ) {
        // utilizamos el texto del enum
        Text(text = enum_color.txt, fontSize = 10.sp)
    }
}
@Preview(showBackground = true)
@Composable
fun IUPreview() {
    IU(miViewModel = MiViewModel())
}