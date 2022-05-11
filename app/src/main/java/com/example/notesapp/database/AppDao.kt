package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.notesapp.model.Notes


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Delete
    suspend fun deleteNote(notes:Notes)

    @Query("SELECT * FROM notes")
    fun getAllNotes():LiveData<List<Notes>>

    @Update
    fun update(note:Notes)
}