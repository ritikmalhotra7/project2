package com.example.notesapp.viewmodel

import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: AppRepository) : ViewModel() {
    fun insert(note: Notes) = viewModelScope.launch {
        repository.upsert(note)
    }
    fun delete(note:Notes) = viewModelScope.launch {
        repository.delete(note)
    }
    fun update(note:Notes) = GlobalScope.launch {
        repository.update(note)
    }
    fun getAll() = repository.getAll()

}