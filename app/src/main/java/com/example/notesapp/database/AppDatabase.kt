package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.Notes

@Database(entities = [Notes::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNotesDao() : AppDao

    companion object{
        @Volatile
        private var instance : AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"appdb.db").build()

    }


}