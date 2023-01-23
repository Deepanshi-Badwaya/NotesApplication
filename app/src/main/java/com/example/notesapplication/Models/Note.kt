package com.example.notesapplication.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**entity class for room
 * this is the class or table where we store our notes data
 * and this class will define that how the object of note will look like*/
@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "title") val title :String?,
    @ColumnInfo(name = "note") val note:String?,
    @ColumnInfo(name = "date") val dates:String?
):java.io.Serializable
