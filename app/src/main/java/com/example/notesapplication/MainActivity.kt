package com.example.notesapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplication.Adapter.NotesAdapter
import com.example.notesapplication.Database.NoteDatabase
import com.example.notesapplication.Models.Note
import com.example.notesapplication.Models.NoteViewModel
import com.example.notesapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , NotesAdapter.NotesClickListener , PopupMenu.OnMenuItemClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    private lateinit var viewModel:NoteViewModel
    lateinit var adapter: NotesAdapter
    private lateinit var selectedNote : Note

    @SuppressLint("SuspiciousIndentation")
    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if (result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as Note
                viewModel.updateNote(note)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing the View
        initUI()

        //initializing view model
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]

        viewModel.allnotes.observe(this){ list ->
            list?.let{
                adapter.updateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)
    }

    //for initializing the notes in recycler view
    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = NotesAdapter(this,this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if (result.resultCode == Activity.RESULT_OK){

                val note = result.data?.getSerializableExtra("note") as? Note
                if (note !=null){
                    viewModel.insertNote(note)
                }
            }
        }

        //click listener for adding the notes
        binding.fbAddNote.setOnClickListener{
            val intent  = Intent(this,AddNote::class.java)
            getContent.launch(intent)
        }

        //for searching the already presented notes
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               if (newText != null){
                   adapter.filterList(newText)
               }
                return  true
            }
        })
    }
    override fun onItemClicked(note: Note) {
        val intent = Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("current note",note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {

        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    //delete the selected note
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId==R.id.delete_note){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}