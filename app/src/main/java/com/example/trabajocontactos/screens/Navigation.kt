package com.example.trabajocontactos.screens

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trabajocontactos.Data.Contact
import com.example.trabajocontactos.Data.Repository
import com.example.trabajocontactos.Data.ViewModelClass
import com.example.trabajocontactos.ui.theme.TrabajoContactosTheme


@Composable
fun MainMenu(context: Context) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        Nagigation(context)
    }
}

private fun transformData(data: List<String>): SnapshotStateList<Contact> {
    val outPut: SnapshotStateList<Contact> = SnapshotStateList()
    data.forEach { item ->
        val line = item.split(";")
        outPut.add(Contact(line[0], line[1], line[2].toInt()))
    }
    return outPut
}

@Composable
fun Nagigation(context: Context) {
    val navigationController = rememberNavController()
    val fileRepository = Repository(context = context)
    val fileViewModel: ViewModelClass = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewModelClass(fileRepository) as T
            }
        }
    )
    fileViewModel.readLines()
    val data by fileViewModel.content.collectAsState()
    var contactList: SnapshotStateList<Contact> = SnapshotStateList()
    fun refreshData() {
        fileViewModel.readLines()
        contactList = transformData(data)
    }
    refreshData()

    NavHost(
        navController = navigationController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {
            Screens.MainScreen(navigationController, contactList){ fileViewModel.writeLine(contactList); refreshData() }
        }
        composable("addScreen") {
            Screens.ModifyContactScreen(navigationController, null, contactList) { fileViewModel.writeLine(contactList); refreshData() }
        }
        composable(
            "modifyScreen/{userId}", arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val contact: Contact? = getContact(userId, contactList)
            Screens.ModifyContactScreen(navigationController, contact, contactList){ fileViewModel.writeLine(contactList); refreshData() }
        }
    }
}

private fun getContact(id: Int, contactList: MutableList<Contact>): Contact? {
    var outPut: Contact? = null
    contactList.forEach { item ->
        if (item.id == id) {
            outPut = item
        }
    }
    return outPut
}
