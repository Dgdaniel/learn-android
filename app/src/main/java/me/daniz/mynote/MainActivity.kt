package me.daniz.mynote


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // late init for variables called in the onCreate methode
    // mutable list in order to retrieve or add a new item to the collection
    private lateinit var notes: MutableList<Note>
    private lateinit var noteAdapter: NoteAdaper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // retrieve the toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        notes = mutableListOf()
        notes.add(Note("Note 1", "Blablabla"))
        notes.add(Note("Mémo Bob", "Grand joueur de basket"))
        notes.add(Note("Mémo Bobette", "Future championne de la NBA"))
        notes.add(Note("Pourquoi Kotlin ?", "Parce-que Java !"))
        noteAdapter = NoteAdaper(notes, this)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter
    }

    // methode according to the api latest version
    private val resultContracts = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result : ActivityResult ->

        if(it.resultCode == Activity.RESULT_OK || it.data == null){

            return@registerForActivityResult
        }

        when(it.data?.extras?.getInt("request_code")){
            DetailsActivity.REQUEST_EDIT_NOTE -> processEditNoteResult()
        }
    }
    override fun onClick(v: View) {
        if(v.tag != null){
            showDetails(v.tag as Int)
            Log.i("NoteList", "Clicked on one item of the list")
        }

    }


    private fun processEditNoteResult(data: Intent){
        val noteIndex =  data.getIntExtra(DetailsActivity.EXTRA_NOTE_INDEX, -1)
        val note = data.getParcelableExtra<Note>(DetailsActivity.EXTRA_NOTE)
        if (note != null) {
            saveNote(note, noteIndex)
        }
    }


    private fun saveNote(note: Note, noteIndex: Int) {
        notes[noteIndex] = note
        noteAdapter.notifyDataSetChanged()
    }


    private fun showDetails(noteIndex: Int){
        val note = notes[noteIndex]
        // to start a new activity remember to use intent
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_NOTE, note)
        intent.putExtra(DetailsActivity.EXTRA_NOTE_INDEX, noteIndex)
       // startActivityForResult(intent, DetailsActivity.REQUEST_EDIT_NOTE)
        resultContracts.launch(intent)
    }
}