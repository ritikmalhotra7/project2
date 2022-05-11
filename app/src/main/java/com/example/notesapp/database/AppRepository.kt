package com.example.notesapp.database

import com.example.notesapp.model.Notes

class AppRepository(val db :AppDatabase) {

    suspend fun upsert(note: Notes) = db.getNotesDao().insert(note)
    suspend fun delete(note:Notes) = db.getNotesDao().deleteNote(note)
    fun getAll() = db.getNotesDao().getAllNotes()
    fun update(note:Notes) = db.getNotesDao().update(note)
}