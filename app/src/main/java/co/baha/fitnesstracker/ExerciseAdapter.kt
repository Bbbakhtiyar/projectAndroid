package co.baha.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.baha.fitnesstracker.model.ExerciseItem

class ExerciseAdapter(private val items: List<ExerciseItem>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_exercise)
        val iconDone: ImageView = view.findViewById(R.id.icon_done)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.name
        holder.iconDone.visibility = if (item.isDone) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            item.isDone = !item.isDone
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = items.size
}