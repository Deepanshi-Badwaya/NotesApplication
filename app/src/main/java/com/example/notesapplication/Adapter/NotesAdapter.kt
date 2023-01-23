package com.example.notesapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.Models.Note
import com.example.notesapplication.R
import kotlin.random.Random

//Adapter class for recycler view
class NotesAdapter(private val context: Context, private val listener :NotesClickListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder> (){
    private val NotesList  = ArrayList<Note>()//this is used to show notes list in recycler view
    private val fullList  =   ArrayList<Note>()//this will contain all the element in the database which we want to fetch

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       return NoteViewHolder(
           LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
       )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.Note_tv.text = currentNote.note
        holder.date.text = currentNote.dates
        holder.title.isSelected = true
        //set the background color of card
        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))

        //setting listener on onItemClicked
        holder.notes_layout.setOnClickListener{
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }
        //setting listener on onLongItemClicked
        holder.notes_layout.setOnLongClickListener{
            listener.onLongItemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
            true

        }


    }

    override fun getItemCount(): Int {
        return  NotesList.size
    }

    //we want to set the full list as new list
    fun updateList(newList:List<Note>){
        fullList.clear() //first we clear all the element inside the full list
        fullList.addAll(newList)//we want to add all element of new list to full list
        NotesList.clear()
        NotesList.addAll(fullList)//we want to add the element of full list to the recycler view
        notifyDataSetChanged()

    }
    //this method is called whenever user clicks on search bar
    fun filterList (search:String){
        NotesList.clear()
        for (item in fullList){
            /** here we will compare if either the title or note matches with the string that the user had typed in the search bar
             * and it add the particular notes to the notes list
             * because we have clear all the notes from   the note list */
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                    item.note?.lowercase()?.contains(search.lowercase()) == true){
                NotesList.add(item)
            }
        }

    }


    //this func randomly hows color to the notes
    @SuppressLint("SuspiciousIndentation")
    private fun randomColor() : Int{
     val list  = ArrayList<Int>()
        list.apply {
            add(R.color.NoteColor1)
            add(R.color.NoteColor2)
            add(R.color.NoteColor3)
            add(R.color.NoteColor4)
            add(R.color.NoteColor5)
            add(R.color.NoteColor6) }


    /** we are using this because it will create a random pattern
     * whenever this method is called seed will change
     * there is no repetition in the flow of current*/
        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]

    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //referring our list item
        val notes_layout: CardView = itemView.findViewById(R.id.card_layout)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val Note_tv: TextView = itemView.findViewById(R.id.tv_note)
        val date: TextView = itemView.findViewById(R.id.tv_date)

    }

    interface NotesClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note,cardView: CardView)
    }
}