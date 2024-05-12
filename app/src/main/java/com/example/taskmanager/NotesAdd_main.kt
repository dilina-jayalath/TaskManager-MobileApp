import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ptatice.Note
import com.example.ptatice.NotesDatabaseHelper
import com.example.ptatice.R
import com.example.ptatice.updateNote

class NotesAdd_main(private var notes: List<Note>, private val context: Context) : RecyclerView.Adapter<NotesAdd_main.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Set the status of the checkbox based on the note's completion status
        holder.checkBox.isChecked = note.completed

        holder.updateButton.setOnClickListener {
            var intent = Intent(holder.itemView.context, updateNote::class.java).apply {
            putExtra("note_id", note.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id)
            refresh(db.getAllDetails())
            Toast.makeText(context, "Note Deleted Successfully", Toast.LENGTH_SHORT).show()
        }

        // Add a listener to the checkbox
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            // Update the note's completion status in the database
            note.completed = isChecked
            db.updateNote(note)
            // Refresh the RecyclerView
            refresh(db.getAllDetails())
            if (isChecked) {
                Toast.makeText(context, "Note Moved to Completed Tasks", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Refresh the adapter with new notes
    fun refresh(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
