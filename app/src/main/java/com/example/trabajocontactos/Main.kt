package com.example.trabajocontactos

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.trabajocontactos.screens.MainMenu
import com.example.trabajocontactos.ui.theme.TrabajoContactosTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabajoContactosTheme {
                MainMenu(context = applicationContext)
            }
        }
    }
}