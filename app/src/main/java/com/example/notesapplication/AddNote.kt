package com.example.notesapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapplication.Models.Note
import com.example.notesapplication.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {
    //accessing our binding class
    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note//this for the updated note
    private lateinit var old_note : Note//this for old note
    //variable for checking the note if the note has been updated or not
    private var isUpdate  = false
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for updating the old note
        try {
            old_note = intent.getSerializableExtra("current note") as Note
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdate = true
        }catch (e : java.lang.Exception){
            e.printStackTrace()
        }//setting click listener on img check to update the chosen note
        binding.imgCheck.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()
            if (title.isNotEmpty() || note_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE,d MMM yyy HH:mm a")//add the note with current date and time
                note = if (isUpdate){
                    Note(
                        old_note.id,title,note_desc,formatter.format(Date())
                    )
                } else{
                    Note(
                        null,title,note_desc,formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else{
                Toast.makeText(this@AddNote,"Please enter Some Data",Toast.LENGTH_LONG).show()//if the note is empty
                return@setOnClickListener
            }

        }

        //when we click o0n the back arrow
        binding.imgBackArrow.setOnClickListener{
            onBackPressed()
        }
    }
}