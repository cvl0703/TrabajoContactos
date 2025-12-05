package com.example.trabajocontactos.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelClass(private val repository: Repository): ViewModel()  {

    private val _content = MutableStateFlow<List<String>>(emptyList())
    val content = _content.asStateFlow()

    fun readLines() {
        viewModelScope.launch {
            _content.value = repository.getContent()
        }
    }

    fun writeLine(list: List<Contact>) {
        viewModelScope.launch {
            repository.writeContent(list)
        }
    }
}