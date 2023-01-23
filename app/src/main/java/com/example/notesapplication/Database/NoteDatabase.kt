package com.example.notesapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapplication.Models.Note
import com.example.notesapplication.utilities.DATABASE_NAME

//database class for room
//and this class is responsible for creating the app for your database
@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase() {

    abstract fun getNoteDao(): NoteDao //accessing/calling our dao
    //there should be one instance of the database in the whole app
    // that's we use companion object
    companion object{
        @Volatile
        private var INSTANCE :NoteDatabase? = null  //object of this class
        //function for accessing our database
        fun getDatabase(context: Context) : NoteDatabase{
            return INSTANCE ?: synchronized(this){//this means that no two operations will carry out at single instance of time
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                 INSTANCE = instance
                instance
            }
        }
    }
}
