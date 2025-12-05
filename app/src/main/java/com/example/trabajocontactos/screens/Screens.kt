package com.example.trabajocontactos.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.trabajocontactos.Data.Contact
import com.example.trabajocontactos.Data.ContactCard

class Screens {
    companion object {
        @Composable
        fun ModifyContactScreen(navController: NavController, contact: Contact?, contactList: MutableList<Contact>, writeData: () -> Unit) {
            Scaffold { paddingValues ->
                if (contact != null){
                    var tlf by remember { mutableStateOf(contact.telephone) }
                    var name by remember { mutableStateOf(contact.name) }
                    Column(modifier = Modifier.fillMaxWidth().padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Modificar un contacto", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row (verticalAlignment = Alignment.CenterVertically){
                            Text("nombre:", modifier = Modifier.fillMaxWidth(0.2f))
                            TextField(placeholder = { "nombre del contacto" }, onValueChange = { value: String -> name = value }, value = name)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row (verticalAlignment = Alignment.CenterVertically){
                            Text("Telefono:", modifier = Modifier.fillMaxWidth(0.2f))
                            TextField(placeholder = { "telefono del contacto" }, onValueChange = { value: String -> tlf = value }, value = tlf)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Button(onClick = { contactList.set(contactList.indexOf(contact), modifyContact(contact.id, tlf, name)); writeData() ; navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                                Text("Guardar", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                                Text("Cancelar", color = Color.Black)
                            }
                        }
                    }
                } else {
                    var tlf by remember { mutableStateOf("") }
                    var name by remember { mutableStateOf("") }
                    val id: Int = (Math.random() * 1000000000).toInt()
                    Column(modifier = Modifier.fillMaxWidth().padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Añadir nuevo contacto", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row (verticalAlignment = Alignment.CenterVertically){
                            Text("nombre:", modifier = Modifier.fillMaxWidth(0.2f))
                            TextField(placeholder = { "nombre del contacto" }, onValueChange = { value: String -> name = value }, value = name)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row (verticalAlignment = Alignment.CenterVertically){
                            Text("Telefono:", modifier = Modifier.fillMaxWidth(0.2f))
                            TextField(placeholder = { "telefono del contacto" }, onValueChange = { value: String -> tlf = value }, value = tlf)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Button(onClick = { contactList.add(modifyContact(id, tlf, name)); writeData() ; navController.popBackStack()  }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                                Text("Guardar", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                                Text("Cancelar", color = Color.Black)
                            }
                        }
                    }
                }
            }
        }

        fun modifyContact(id: Int, tlf: String, name: String): Contact{
            return Contact(name, tlf, id)
        }

        @Composable
        fun MainScreen(navController: NavController, contactList: MutableList<Contact>, writeData: () -> Unit) {
            Scaffold (
                floatingActionButton = {
                    FloatingActionButton (
                        onClick = {
                            navController.navigate("addScreen")
                        },
                        containerColor = Color.White,
                    ) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "add", tint = Color.Black)
                    }
                }
            ) { paddingValues ->
                if (contactList.isNotEmpty()){
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(300.dp),
                        contentPadding = paddingValues,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(items = contactList){ contact ->
                            ContactCard(contact, navController, contactList){ writeData() }
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center){
                            Text("No tienes contactos en tu lista")
                        }
                    }
                }
            }

        }

    }
}