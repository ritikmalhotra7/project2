package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "notes"
)
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val heading:String,
    val body : String,
    val date : String,
    val type : String
): Serializable{
    override fun toString(): String {
        return "Notes(id=$id, heading='$heading', body='$body', date='$date', type='$type')"
    }
}
