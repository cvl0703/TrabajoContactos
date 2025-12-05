package com.example.trabajocontactos.Data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Contact(
    var name: String,
    var telephone: String,
    var id: Int
){
    override fun toString(): String {
        return "${name};${telephone};${id}"
    }
}
@Composable
fun ContactCard(
    contact: Contact,
    navController: NavController,
    contactList: MutableList<Contact>,
    writeData: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nombre: ${contact.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row {
                Text(
                    text = "Teléfono: ${contact.telephone}",
                    fontSize = 18.sp
                )

            }
        }
        Column {
            ElevatedButton(
                onClick = {
                    navController.navigate("modifyScreen/${contact.id}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text("Modificar contacto", color = Color.Black)
            }
            ElevatedButton(
                onClick = {
                    contactList.remove(contact)
                    writeData()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text("Eliminar contacto")
            }
        }
    }
}