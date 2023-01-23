package com.example.notesapplication.Database

import androidx.lifecycle.LiveData
import com.example.notesapplication.Models.Note

//repository class to communicate with the database
class NotesRepository(private val noteDao : NoteDao) {
    val allNotes :LiveData<List<Note>> = noteDao.getAllNotes() // calling getAllNotes from NoteDao

    //calling function from noteDao
    suspend fun insert(note:Note){
        noteDao.insert(note)
    }
    suspend fun delete (note: Note){
        noteDao.delete(note)
    }
    suspend fun update(note: Note){
        noteDao.update(note.id,note.title,note.note)
    }
}