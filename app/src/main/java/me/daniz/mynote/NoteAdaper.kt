package me.daniz.mynote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdaper(val notes: List<Note>, private val itemClickListener: View.OnClickListener) :
    RecyclerView.Adapter<NoteAdaper.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val cardView: CardView = view.findViewById<CardView>(R.id.card_view)
        val titleView: TextView = view.findViewById<TextView>(R.id.title)
        val excerptView: TextView = view.findViewById<TextView>(R.id.excerpt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       // return un viewHolder pour retourner le layout ou le menu ...
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return  ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind les data par rapport a la vue
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.titleView.text = note.title
        holder.excerptView.text = note.text

    }

    override fun getItemCount(): Int {
        return notes.size
    }
}