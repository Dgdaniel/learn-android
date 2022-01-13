package me.daniz.mynote

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class DetailsActivity : AppCompatActivity() {
    val REQUEST_CODE = 1
    companion object{
         val REQUEST_EDIT_NOTE = 1
         val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
    }
    private lateinit var note : Note
    private lateinit var titleView: TextView
    private lateinit var textView: TextView
    private var noteIndex: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)!!
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)
        titleView = findViewById(R.id.title)
        textView = findViewById(R.id.body)
        titleView.text = note.title
        textView.text = note.text
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

         when (item.itemId) {
            R.id.save -> {
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    private fun saveNote(){
        note.title = titleView.text.toString()
        note.text = textView.text.toString()
        intent = Intent()
        intent.putExtra(EXTRA_NOTE, note)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        intent.putExtra("request_code", REQUEST_CODE)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}