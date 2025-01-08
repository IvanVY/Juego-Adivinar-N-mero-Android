package com.example.juegoadivinarnumero

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var numeroAleatorio: Int = 0
    private var intentosRestantes: Int = 5

    private lateinit var textNumber: EditText
    private lateinit var botonAdivinar: Button
    private lateinit var viewMensajes: TextView
    private lateinit var viewIntentosRestantes: TextView
    private lateinit var botonReiniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Inicializar vista
        textNumber = findViewById(R.id.textNumber)
        botonAdivinar = findViewById(R.id.botonAdivinar)
        viewMensajes = findViewById(R.id.viewMensajes)
        viewIntentosRestantes = findViewById(R.id.viewIntentosRestantes)
        botonReiniciar = findViewById(R.id.botonReiniciar)

        //Generar numero aleatorio al iniciar
        reiniciarJuego()

        //Configurar el boton adivinar
        botonAdivinar.setOnClickListener {
            val intento = textNumber.text.toString().toIntOrNull()
            if (intento != null && intento in 1..20){
                validarIntento(intento)
            }else{
                viewMensajes.text = "Ingrese un número válido entre 1 y 20"
            }

        }

        //Configurar boton reiniciar
        botonReiniciar.setOnClickListener {
            reiniciarJuego()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun validarIntento(intento: Int){

        if (intento == numeroAleatorio){
            viewMensajes.text = "Correcto, Has adivinado el número."
            deshabilitarJuego()  // Deshabilitar el juego
        }else if(intento > numeroAleatorio){
            viewMensajes.text = "Demasiado alto. Intenta un número más bajo."
            intentosRestantes -= 1
        }else{
            viewMensajes.text = "Demasiado bajo. Intenta un número más alto."
            intentosRestantes -= 1
        }

        //actualizar contador de intentos
        viewIntentosRestantes.text = "Intentos restantes: $intentosRestantes"

        //verificar si se ha agotado los intentos
        if (intentosRestantes <= 0){
            viewMensajes.text = "Has perdido. El número era $numeroAleatorio."
            deshabilitarJuego()  // Deshabilitar el juego
        }

    }

    private fun deshabilitarJuego(){
        // Deshabilitar el botón Adivinar
        botonAdivinar.isEnabled = false

        // Hacer visible el botón de Reiniciar
        botonReiniciar.visibility = View.VISIBLE
    }

    private fun reiniciarJuego(){
        numeroAleatorio = (1..20).random()//
        intentosRestantes = 5
        viewIntentosRestantes.text = "Intentos restantes: $intentosRestantes"
        viewMensajes.text = "Comienza a jugar"
        // Restablecer el botón de adivinación a habilitado
        botonAdivinar.isEnabled = true
        botonReiniciar.visibility = View.GONE
        textNumber.text.clear()
    }

}