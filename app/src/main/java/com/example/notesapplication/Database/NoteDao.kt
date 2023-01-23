package com.example.notesapplication.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesapplication.Models.Note

//dao interface for room
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//This method replaces the note if there is any conflict
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table order by id ASC") // select all the notes from table according to the ascending id's
    fun getAllNotes() :LiveData<List<Note>>

    @Query("UPDATE notes_table set title = :title, note =:note where id =:id")//for updating the note
    suspend fun update(id  :Int?, title : String?, note : String?)
}